package ltd.v2.ppl.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ltd.v2.ppl.auth.domain.use_case.getCampaignListData
import ltd.v2.ppl.auth.domain.use_case.getSignInData
import ltd.v2.ppl.auth.domain.use_case.getUserInfoData
import ltd.v2.ppl.common_utils.constants.AppConstant
import ltd.v2.ppl.core.data_source.app_pref.AppPreference
import ltd.v2.ppl.core.domain.Result

class LoginViewModel(
    private val getSignInData: getSignInData,
    private val getUserInfoData: getUserInfoData,
    private val getCampaignListData: getCampaignListData,
    private val controller: PermissionsController,
    private val appPref: AppPreference,
    private val connectivity: Connectivity,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _oneTimeState = MutableSharedFlow<LoginState>()
    val oneTimeState: SharedFlow<LoginState> = _oneTimeState


    private val requiredPermissions = listOf(
        Permission.RECORD_AUDIO,
        Permission.STORAGE,
        Permission.LOCATION,
        Permission.COARSE_LOCATION,
        Permission.CAMERA
    )

    init {
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
            LoginEvents.RequestPermissions -> handleFirstPermissionRequest()
            LoginEvents.RequestPermissionsFromDenied -> requestPermissionsFromDenied()
            LoginEvents.OnLoginClicked -> {
                checkPermissionsBeforeLogin()
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
             _state.update { it.copy(isLoginBtnLoading = true) }

            val signInMap: Map<String, Any?> = mapOf(
                "username" to username,
                "password" to password,
                "platform" to 61
            )


            when (val result = getSignInData(signInMap)) {
                is Result.Success -> {
                    _state.update { it.copy(isLoginBtnLoading = false) }
                    AppConstant.accessList = result.data.accessList?.toMutableList() ?: mutableListOf()

                    callUserApi(result.data.token)
                }
                is Result.Error -> {
                    println("Error result is ${result.error.message}")
                    _state.update { it.copy(isLoginBtnLoading = false) }
                }
            }
        }
    }

    private fun callUserApi(token: String) {

        viewModelScope.launch {
            when(val result = getUserInfoData(token)){
                is Result.Success -> {
                    AppConstant.accessList = result.data.accessList ?: mutableListOf()
                    if(AppConstant.accessList.contains(AppConstant.CONTACT_MODULE)){
                        getCampaignList(token)
                    }else if(AppConstant.accessList.contains(AppConstant.JOINT_CALL_MODULE)){

                    }else{
                        _oneTimeState.emit(LoginState(noAccess = true))
                    }


                }

                is Result.Error -> {
                    println("Error result is ${result.error.message}")
                    _state.update { it.copy(isLoginBtnLoading = false) }
                }
            }
        }
    }

    private fun getCampaignList(token: String){
        viewModelScope.launch {
            when(val result = getCampaignListData(token)){
                is Result.Success -> {
                    println(result.data[0].name)
                }
                is Result.Error -> {
                    println("Error result is ${result.error.message}")
                    _state.update { it.copy(isLoginBtnLoading = false) }
                }
            }
        }
    }




}







