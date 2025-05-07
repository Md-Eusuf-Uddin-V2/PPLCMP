package ltd.v2.ppl.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devx.kdeviceinfo.DeviceInfoXState
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.jordond.connectivity.Connectivity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.timeout
import io.ktor.client.request.head
import io.ktor.client.request.prepareGet
import io.ktor.client.statement.HttpStatement
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.http.contentLength
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.cancel
import io.ktor.utils.io.readAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.v2.ppl.auth.domain.model.DownloadModel
import ltd.v2.ppl.auth.domain.model.DownloadProgress
import ltd.v2.ppl.auth.domain.model.MediaFile
import ltd.v2.ppl.auth.domain.use_case.GetAllCampaignData
import ltd.v2.ppl.auth.domain.use_case.GetCampaignDataById
import ltd.v2.ppl.auth.domain.use_case.InsertCampaignData
import ltd.v2.ppl.auth.domain.use_case.UpdateCampaignData
import ltd.v2.ppl.auth.domain.use_case.getCampaignListData
import ltd.v2.ppl.auth.domain.use_case.getSignInData
import ltd.v2.ppl.auth.domain.use_case.getSurveyModelData
import ltd.v2.ppl.auth.domain.use_case.getUserInfoData
import ltd.v2.ppl.common_utils.constants.AppConstant
import ltd.v2.ppl.common_utils.utils.FileHelper
import ltd.v2.ppl.common_utils.utils.getAppPackageName
import ltd.v2.ppl.core.app_url.AppUrl
import ltd.v2.ppl.core.data_source.app_pref.AppPreference
import ltd.v2.ppl.core.data_source.remote.HttpClientFactory
import ltd.v2.ppl.core.domain.Result
import okio.Sink
import okio.buffer
import okio.use
import kotlin.time.Duration.Companion.minutes

class LoginViewModel(
    private val getAllCampaignData: GetAllCampaignData,
    private val updateCampaignData: UpdateCampaignData,
    private val insertCampData: InsertCampaignData,
    private val getCampaignDataById: GetCampaignDataById,
    private val getSignInData: getSignInData,
    private val getUserInfoData: getUserInfoData,
    private val getCampaignListData: getCampaignListData,
    private val getSurveyDataResponse: getSurveyModelData,
    private val controller: PermissionsController,
    private val appPref: AppPreference,
    private val connectivity: Connectivity,
    private val deviceInfoXState: DeviceInfoXState,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _oneTimeState = MutableSharedFlow<LoginState>()
    val oneTimeState: SharedFlow<LoginState> = _oneTimeState

    var campCount = 0
    var currentMediaFile = 0
    var totalMediaFile = 0


    private val requiredPermissions = listOf(
        Permission.RECORD_AUDIO,
        Permission.STORAGE,
        Permission.LOCATION,
        Permission.COARSE_LOCATION,
        Permission.CAMERA
    )

    init {
        campCount = 0
        checkFirstLogin()
    }

    fun onUsernameChanged(newUsername: String) {
        _state.value = _state.value.copy(username = newUsername)
    }

    fun onPasswordChanged(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }


    fun processIntent(intent: LoginEvents) {
        when (intent) {
            is LoginEvents.RequestPermissions -> handleFirstPermissionRequest()
            is LoginEvents.RequestPermissionsFromDenied -> requestPermissionsFromDenied()
            is LoginEvents.OnLoginClicked -> {
                checkPermissionsBeforeLogin()
            }

            is LoginEvents.OnFilesDownloadStart -> {
                startFilesDownload(intent.index)
            }
        }
    }


    private fun checkFirstLogin() {
        viewModelScope.launch {
            val isFirstLogin = appPref.isFirstLogin.first()

            if (isFirstLogin) {
                _state.update { it.copy(showPermissionInfoPopup = true) }
            } else {
                requestPermissions()
            }
        }
    }

    private fun handleFirstPermissionRequest() {
        viewModelScope.launch {
            appPref.setFirstLoginDone()

            _state.update {
                it.copy(showPermissionInfoPopup = false)
            }

            requestPermissions()
        }
    }

    private fun requestPermissionsFromDenied() {
        viewModelScope.launch {
            _state.update { it.copy(permissionDeniedPopup = false) }
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        viewModelScope.launch {
            requiredPermissions.forEach { permission ->
                try {
                    if (!controller.isPermissionGranted(permission)) {
                        controller.providePermission(permission)
                    }
                } catch (_: Exception) {

                }
            }
        }
    }

    private fun checkPermissionsBeforeLogin() {
        viewModelScope.launch {
            val allGranted = requiredPermissions.all { permission ->
                try {
                    println("Permission $permission is : ${controller.getPermissionState(permission)}")
                    controller.isPermissionGranted(permission)
                } catch (e: Exception) {
                    false
                }
            }

            if (allGranted) {
                gotoLoginCheck()
            } else {
                _state.update { it.copy(permissionDeniedPopup = true) }
            }
        }
    }

    private fun gotoLoginCheck() {
        viewModelScope.launch {

            if (!connectivity.status().isConnected) {
                _oneTimeState.emit(LoginState(noInternetAvailable = true))
                return@launch
            }


            val isUsernameValid = state.value.username.isNotBlank()
            val isPasswordValid = state.value.password.isNotBlank()

            if (!isUsernameValid || !isPasswordValid) {
                _state.update {
                    it.copy(
                        usernameError = !isUsernameValid,
                        passwordError = !isPasswordValid
                    )
                }
                return@launch
            } else {
                _state.update {
                    it.copy(
                        usernameError = false,
                        passwordError = false
                    )
                }
            }

            callSignInApi(state.value.username, state.value.password)
        }
    }

    private fun callSignInApi(username: String, password: String) {
        viewModelScope.launch {
            campCount = 0
            totalMediaFile = 0
            currentMediaFile = 0
            _state.update { it.copy(isDownloadDialogShow = true) }

            _state.update {
                it.copy(
                    downloadList = mutableListOf(
                        DownloadModel(
                            type = "api",
                            title = "Getting Information",
                            downloadProgress = DownloadProgress(
                                currentFileIndex = 0,
                                progress = 0.0,
                                totalFiles = 4
                            )

                        )
                    )
                )
            }

            val signInMap: Map<String, Any> = mapOf(
                "username" to username,
                "password" to password,
                "deviceInfo" to getDeviceInfo(),
                "platform" to 61
            )


            when (val result = getSignInData(signInMap)) {
                is Result.Success -> {
                    AppConstant.accessList =
                        result.data.accessList?.toMutableList() ?: mutableListOf()

                    _state.update {
                        it.copy(
                            downloadList = mutableListOf(
                                DownloadModel(
                                    type = "api",
                                    title = "Getting Information",
                                    downloadProgress = DownloadProgress(
                                        currentFileIndex = 1,
                                        progress = 0.25,
                                        totalFiles = 4
                                    )

                                )
                            )
                        )
                    }

                    callUserApi(result.data.token)
                }

                is Result.Error -> {
                    println("Error result is ${result.error.message}")
                    _state.update { it.copy(isDownloadDialogShow = false) }
                    _oneTimeState.emit(LoginState(error = result.error.message))
                }
            }
        }
    }

    private fun callUserApi(token: String) {

        viewModelScope.launch {
            when (val result = getUserInfoData(token)) {
                is Result.Success -> {
                    AppConstant.accessList = result.data.accessList ?: mutableListOf()
                    _state.update {
                        it.copy(
                            downloadList = mutableListOf(
                                DownloadModel(
                                    type = "api",
                                    title = "Getting Information",
                                    downloadProgress = DownloadProgress(
                                        currentFileIndex = 2,
                                        progress = 0.50,
                                        totalFiles = 4
                                    )

                                )
                            )
                        )
                    }

                    if (AppConstant.accessList.contains(AppConstant.CONTACT_MODULE)) {
                        getCampaignList(token)
                    } /*else if (AppConstant.accessList.contains(AppConstant.JOINT_CALL_MODULE)) {

                    }*/ else {
                        _state.update { it.copy(isDownloadDialogShow = false) }
                        _oneTimeState.emit(LoginState(noAccess = true))
                    }


                }

                is Result.Error -> {
                    println("Error result is ${result.error.message}")
                    _state.update { it.copy(isDownloadDialogShow = false) }
                    _oneTimeState.emit(LoginState(error = result.error.message))
                }
            }
        }
    }

    private fun getCampaignList(token: String) {
        viewModelScope.launch {
            when (val result = getCampaignListData(token)) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            downloadList = mutableListOf(
                                DownloadModel(
                                    type = "api",
                                    title = "Getting Information",
                                    downloadProgress = DownloadProgress(
                                        currentFileIndex = 3,
                                        progress = 0.75,
                                        totalFiles = 4
                                    )

                                )
                            )
                        )
                    }
                    getSurveyData(token, result.data[campCount].id!!)
                }

                is Result.Error -> {
                    println("Error result is ${result.error.message}")
                    _state.update { it.copy(isDownloadDialogShow = false) }
                    _oneTimeState.emit(LoginState(error = result.error.message))
                }
            }
        }
    }

    private fun getSurveyData(token: String, campaignId: Int) {
        viewModelScope.launch {
            when (val result = getSurveyDataResponse(token, campaignId)) {
                is Result.Success -> {
                    withContext(Dispatchers.IO) {
                        when (val campData = getCampaignDataById(
                            campId = campaignId.toString(),
                            userId = appPref.getLoginData().id!!.toString()
                        )) {
                            is Result.Success -> {
                                if (campData.data == null) {
                                    when (val insertResult = insertCampData(
                                        campData = result.data,
                                        campaignId = campaignId.toString(),
                                        userId = appPref.getLoginData().id!!.toString(),
                                        accessId = AppConstant.CONTACT_MODULE.toString()
                                    )) {
                                        is Result.Success -> {
                                            campCount++
                                            if (appPref.getCampaignData().size == campCount) {
                                                appPref.storeCampaignId(appPref.getCampaignData()[0].id.toString())
                                                appPref.storeCampaignName(appPref.getCampaignData()[0].name.toString())
                                                _state.update {
                                                    it.copy(
                                                        downloadList = mutableListOf(
                                                            DownloadModel(
                                                                type = "api",
                                                                title = "Getting Information",
                                                                downloadProgress = DownloadProgress(
                                                                    currentFileIndex = 4,
                                                                    progress = 1.00,
                                                                    totalFiles = 4
                                                                )

                                                            )
                                                        )
                                                    )
                                                }

                                               getDownloadFiles()

                                            } else {
                                                getSurveyData(
                                                    token = token,
                                                    campaignId = appPref.getCampaignData()[campCount].id!!
                                                )
                                            }
                                            println("Insert data ${insertResult.data}")
                                        }

                                        is Result.Error -> {
                                            _state.update { it.copy(isDownloadDialogShow = false) }
                                            _oneTimeState.emit(LoginState(error = insertResult.error.message))
                                        }

                                    }
                                } else {
                                    when (val isUpdated = updateCampaignData(
                                        campData = result.data, campaignId = campaignId.toString(),
                                        userId = appPref.getLoginData().id!!.toString()
                                    )) {
                                        is Result.Success -> {
                                            println("Update dataaaaaaa ${isUpdated.data}")
                                            campCount++
                                            if (appPref.getCampaignData().size == campCount) {
                                                appPref.storeCampaignId(appPref.getCampaignData()[0].id.toString())
                                                appPref.storeCampaignName(appPref.getCampaignData()[0].name.toString())
                                                _state.update {
                                                    it.copy(
                                                        downloadList = mutableListOf(
                                                            DownloadModel(
                                                                type = "api",
                                                                title = "Getting Information",
                                                                downloadProgress = DownloadProgress(
                                                                    currentFileIndex = 4,
                                                                    progress = 1.00,
                                                                    totalFiles = 4
                                                                )

                                                            )
                                                        )
                                                    )
                                                }

                                                getDownloadFiles()

                                            } else {
                                                getSurveyData(
                                                    token = token,
                                                    campaignId = appPref.getCampaignData()[campCount].id!!
                                                )
                                            }
                                        }

                                        is Result.Error -> {
                                            _state.update { it.copy(isDownloadDialogShow = false) }
                                            _oneTimeState.emit(LoginState(error = isUpdated.error.message))
                                        }
                                    }
                                }
                            }

                            is Result.Error -> {
                                _state.update { it.copy(isDownloadDialogShow = false) }
                                _oneTimeState.emit(LoginState(error = campData.error.message))
                            }
                        }
                    }

                }

                is Result.Error -> {
                    println("Error result is ${result.error.message}")
                    _state.update { it.copy(isDownloadDialogShow = false) }
                    _oneTimeState.emit(LoginState(error = result.error.message))
                }
            }

        }
    }


    private fun getDownloadFiles() {
        viewModelScope.launch(Dispatchers.IO) {

            val imageList: MutableList<MediaFile> = mutableListOf(
                MediaFile("https://user-images.githubusercontent.com/132100144/235250068-74c909ff-8a2c-4539-990f-4999ecd8e379.png"),
                MediaFile("https://user-images.githubusercontent.com/132100144/235250068-74c909ff-8a2c-4539-990f-4999ecd8e379.png"),
                MediaFile("https://download.samplelib.com/png/sample-boat-400x300.png"),
                MediaFile("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_1280.jpg"),
                MediaFile("https://yavuzceliker.github.io/sample-images/image-1.jpg")
            )
            val videoList: MutableList<MediaFile> = mutableListOf(
                MediaFile(url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
                MediaFile(url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4")
            )

            when (val result = getAllCampaignData(appPref.getLoginData().id!!.toString())) {
                is Result.Success -> {
                    if (result.data.isNotEmpty()) {
                        for (i in result.data.indices) {
                            result.data[i]?.image?.map {
                                imageList.add(element = MediaFile("${AppUrl.DOWNLOAD_BASE_URL}$it"))
                            }

                            result.data[i]?.video?.map {
                                videoList.add(element = MediaFile("${AppUrl.DOWNLOAD_BASE_URL}$it"))
                            }

                        }

                        if (imageList.isNotEmpty()) {
                            totalMediaFile += imageList.size
                            _state.update {
                                val updatedList = it.downloadList.toMutableList()
                                updatedList.add(
                                    DownloadModel(
                                        type = "image",
                                        title = "Images",
                                        mediaFiles = imageList,
                                        downloadProgress = DownloadProgress(
                                            currentFileIndex = 0,
                                            progress = 0.0,
                                            totalFiles = 0

                                        )
                                    )
                                )
                                it.copy(downloadList = updatedList)
                            }
                        }

                        if (videoList.isNotEmpty()) {
                            totalMediaFile += videoList.size
                            _state.update {
                                val updatedList = it.downloadList.toMutableList()
                                updatedList.add(
                                    DownloadModel(
                                        type = "video",
                                        title = "Videos",
                                        mediaFiles = videoList,
                                        downloadProgress = DownloadProgress(
                                            currentFileIndex = 0,
                                            progress = 0.0,
                                            totalFiles = 0
                                        )
                                    )
                                )
                                it.copy(downloadList = updatedList)
                            }
                        }

                        if (state.value.downloadList.size == 1) {
                           if(appPref.getAttendanceInfo().checkInStatus != null && appPref.getAttendanceInfo().checkInStatus == true){
                               _oneTimeState.emit(LoginState(shouldNavigateToDashBoard = true))
                               delay(timeMillis = 500)
                               _state.update { it.copy(isDownloadDialogShow = false) }

                           }else{
                               _oneTimeState.emit(LoginState(shouldNavigateToAttendance = true))
                               delay(timeMillis = 500)
                               _state.update { it.copy(isDownloadDialogShow = false) }

                           }
                        }


                    }
                }

                is Result.Error -> {
                    println("Error result is ${result.error.message}")
                    _state.update { it.copy(isDownloadDialogShow = false) }
                    _oneTimeState.emit(LoginState(error = result.error.message))
                }
            }

        }

    }


    private fun startFilesDownload(index: Int) {
        val currentModel = state.value.downloadList[index]
        val updatedModel = currentModel.copy(
            downloadProgress = currentModel.downloadProgress?.copy(
                currentFileIndex = 0,
                progress = 0.0,
                totalFiles = currentModel.mediaFiles?.size ?: 0
            )
        )

        _state.update {
            val updatedList = it.downloadList.toMutableList()
            updatedList[index] = updatedModel
            it.copy(downloadList = updatedList)
        }

        startDownload(updatedModel, index)
    }


    private fun startDownload(downloadModel: DownloadModel, index: Int) {
        viewModelScope.launch {
            val fileCount = downloadModel.mediaFiles?.size ?: return@launch
            var progressTemp = 0.0
            var progressTempRecent = 0.0

            for (i in 0 until downloadModel.mediaFiles.size) {
                if (FileHelper.fileExists(
                        getAppPackageName(),
                        downloadModel.mediaFiles[i].url.substringAfterLast("/")
                    )
                ) {
                    progressTempRecent = (1.0 / (fileCount.toDouble() / 100.0)) / 100.0

                    updateDownloadState(
                        i + 1,
                        progressTemp + progressTempRecent,
                        index,
                        fileCount
                    )
                    delay(timeMillis = 50)
                    currentMediaFile++
                    progressTemp += progressTempRecent
                    continue
                }

                val output = FileHelper.createFileSink(
                    getAppPackageName(),
                    downloadModel.mediaFiles[i].url.substringAfterLast("/")
                )

                downloadFileWithProgress(downloadModel.mediaFiles[i].url, output) { progress ->
                    progressTempRecent = (progress / (fileCount.toDouble() / 100.0)) / 100.0
                    println("Progress is ${progressTempRecent+progressTemp}")
                    updateDownloadState(
                        i + 1,
                        progressTemp + progressTempRecent,
                        index,
                        fileCount
                    )
                }

                currentMediaFile++
                progressTemp += progressTempRecent

            }

           if(totalMediaFile == currentMediaFile){
               if(appPref.getAttendanceInfo().checkInStatus != null && appPref.getAttendanceInfo().checkInStatus == true){
                   _oneTimeState.emit(LoginState(shouldNavigateToDashBoard = true))
                   delay(timeMillis = 500)
                   _state.update { it.copy(isDownloadDialogShow = false) }

               }else{
                   _oneTimeState.emit(LoginState(shouldNavigateToAttendance = true))
                   delay(timeMillis = 500)
                   _state.update { it.copy(isDownloadDialogShow = false) }

               }
           }

        }


    }


    private fun updateDownloadState(
        fileIndex: Int,
        progress: Double,
        index: Int,
        totalSize: Int
    ) {
        _state.update { currentState ->
            val updatedList = currentState.downloadList.toMutableList()

            val updatedItem = updatedList[index].copy(
                downloadProgress = DownloadProgress(
                    currentFileIndex = fileIndex,
                    progress = progress,
                    totalFiles = totalSize
                )
            )
            updatedList[index] = updatedItem

            currentState.copy(downloadList = updatedList)
        }
    }


    private suspend fun downloadFileWithProgress(
        url: String,
        outputStream: Sink,
        onProgress: (Double) -> Unit
    ) {
        try {
            val client: HttpClient = HttpClientFactory.makeClient(false)
            val statement: HttpStatement = client.prepareGet(url) {
                timeout {
                    requestTimeoutMillis = 30.minutes.inWholeMilliseconds
                }

            }
            statement.execute { response ->
                val channel: ByteReadChannel = response.body()
                val contentLen = response.contentLength() ?: -1L
                val buffer = ByteArray(1024 * 100)

                outputStream.buffer().use { sink ->
                    var totalRead = 0L
                    while (!channel.isClosedForRead) {
                        val bytesRead = channel.readAvailable(buffer)
                        if (bytesRead <= 0) break
                        sink.write(buffer, 0, bytesRead)
                        totalRead += bytesRead
                        if (contentLen > 0) {
                            val progress = (totalRead / contentLen.toDouble())
                            onProgress(progress)
                        }
                    }

                    sink.flush()
                    onProgress(1.0)
                }
                channel.cancel()
            }
        } catch (e: Exception) {
            println("Download failed: $e")
        }
    }

    private fun getDeviceInfo(): Map<String, Any> {
        return if (deviceInfoXState.isAndroid) {
            val androidInfo = deviceInfoXState.androidInfo
            mapOf(
                "device_id" to androidInfo.androidId,
                "app_version" to androidInfo.versionName,
                "security_patch" to androidInfo.version.securityPatch,
                "ui_version" to androidInfo.version.incremental,
                "android_version" to androidInfo.version.release,
                "api_version" to androidInfo.version.sdkInt.toString(),
                "manufacture" to androidInfo.manufacturer,
                "user_type" to "user",
                "app_version_code" to androidInfo.versionCode.toString(),
                "model" to androidInfo.model,
                "network_type" to "wifi",
                "brand" to androidInfo.manufacturer
            )
        } else if (deviceInfoXState.isIos) {
            val iosInfo = deviceInfoXState.iosInfo
            mapOf(
                "device_id" to iosInfo.identifierForVendor,
                "app_version" to iosInfo.appVersion,
                "security_patch" to "",
                "ui_version" to iosInfo.appVersion,
                "android_version" to iosInfo.systemVersion,
                "api_version" to iosInfo.appShortVersion,
                "manufacture" to iosInfo.systemName,
                "user_type" to iosInfo.name,
                "app_version_code" to 0,
                "model" to iosInfo.model,
                "network_type" to "wifi",
                "brand" to iosInfo.localizedModel
            )
        } else {
            mapOf(
                "device_id" to "",
                "app_version" to "",
                "security_patch" to "",
                "ui_version" to "",
                "android_version" to "",
                "api_version" to "",
                "manufacture" to "",
                "user_type" to "",
                "app_version_code" to 0,
                "model" to "",
                "network_type" to "",
                "brand" to ""
            )
        }
    }
}







