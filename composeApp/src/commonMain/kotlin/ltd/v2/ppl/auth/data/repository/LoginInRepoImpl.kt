package ltd.v2.ppl.auth.data.repository

import com.devx.kdeviceinfo.DeviceInfoXState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import ltd.v2.ppl.auth.data.mappers.toDomain
import ltd.v2.ppl.auth.data.response_model.CampaignsModel
import ltd.v2.ppl.auth.data.response_model.SignInResponse
import ltd.v2.ppl.auth.data.response_model.UserInfoModel
import ltd.v2.ppl.auth.domain.model.CampaignListDomainModel
import ltd.v2.ppl.auth.domain.model.DeviceInfoRequest
import ltd.v2.ppl.auth.domain.model.SignInDomainModel
import ltd.v2.ppl.auth.domain.model.SignInRequest
import ltd.v2.ppl.auth.domain.model.UserDataDomainModel
import ltd.v2.ppl.auth.domain.repository.LogInRepo
import ltd.v2.ppl.core.app_url.AppUrl
import ltd.v2.ppl.core.data_source.app_pref.AppPreference
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.LocalError
import ltd.v2.ppl.core.domain.RemoteError
import ltd.v2.ppl.core.domain.Result

class LoginInRepoImpl(
    private val httpClient: HttpClient,
    private val deviceInfoXState: DeviceInfoXState,
    private val appPref: AppPreference
) : LogInRepo {

    override suspend fun getSignInDataResponse(signInMap: Map<String, Any?>): Result<SignInDomainModel, DataError> {
        return try {

            val request = SignInRequest(
                username = signInMap["username"] as String,
                password = signInMap["password"] as String,
                deviceInfo = getDeviceInfo(),
                platform = signInMap["platform"] as Int
            )

            val response = httpClient.post {
                url("${AppUrl.BASE_URL}${AppUrl.Auth.SIGN_IN_URL}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            if (response.status.isSuccess()) {
                val responseBody: SignInResponse = response.body()
                appPref.setLoginData(Json.encodeToString(responseBody.data))
                Result.Success(responseBody.data!!.toDomain())
            } else {
                val errorMessage = try {
                    val jsonResponse = Json.parseToJsonElement(response.bodyAsText()) as JsonObject
                    jsonResponse["message"]?.jsonPrimitive?.content
                        ?: "Service is currently unavailable. Please try again shortly."
                } catch (e: Exception) {
                    "Service is currently unavailable. Please try again shortly."
                }

                Result.Error(RemoteError(errorMessage))
            }
        } catch (e: Exception) {
            Result.Error(LocalError("Something went wrong"))
        }
    }

    override suspend fun getUserInfoData(token: String): Result<UserDataDomainModel, DataError> {
        return try {

            val response = httpClient.get{
                url("${AppUrl.BASE_URL}${AppUrl.Auth.USER_INFO_URL}")
                header("Authorization", "Bearer $token")
            }

            if(response.status.isSuccess()){
                val userInfoModel : UserInfoModel = response.body()
                val userDataList = userInfoModel.data

                if (!userDataList.isNullOrEmpty()) {
                    Result.Success(userDataList[0].toDomain())
                } else {
                    Result.Error(LocalError("No User Data Found"))
                }

            }else{
                val errorMessage = try {
                    val jsonResponse = Json.parseToJsonElement(response.bodyAsText()) as JsonObject
                    jsonResponse["message"]?.jsonPrimitive?.content
                        ?: "Service is currently unavailable. Please try again shortly."
                } catch (e: Exception) {
                    "Service is currently unavailable. Please try again shortly."
                }
                Result.Error(RemoteError(errorMessage))
            }
        } catch (e: Exception) {
            Result.Error(LocalError("Something went wrong"))
        }
    }

    override suspend fun getCampaignListResponse(token: String): Result<List<CampaignListDomainModel>, DataError> {
        return try {
            val response = httpClient.get {
                url("${AppUrl.BASE_URL}${AppUrl.Auth.CAMPAIGN_LIST_URL}")
                header("Authorization", "Bearer $token")
            }

            if (response.status.isSuccess()) {
                val campaignListModel: CampaignsModel = response.body()
                val campaignList = campaignListModel.campaignList

                if (!campaignList.isNullOrEmpty()) {
                    appPref.setUserInfoData(Json.encodeToString(campaignList))
                    Result.Success(campaignList.map { it.toDomain() })
                } else {
                    Result.Error(LocalError("No Campaign Data Found"))
                }

            } else {

                val errorMessage = try {
                    val jsonResponse = Json.parseToJsonElement(response.bodyAsText()) as JsonObject
                    jsonResponse["message"]?.jsonPrimitive?.content
                        ?: "Service is currently unavailable. Please try again shortly."
                } catch (e: Exception) {
                    "Service is currently unavailable. Please try again shortly."
                }
                Result.Error(RemoteError(errorMessage))
            }
        } catch (e: Exception) {
            Result.Error(LocalError("Something went wrong"))
        }
    }


    private fun getDeviceInfo(): DeviceInfoRequest {
        return if (deviceInfoXState.isAndroid) {
            val androidInfo = deviceInfoXState.androidInfo
            DeviceInfoRequest(
                device_id = androidInfo.androidId,
                app_version = androidInfo.versionName,
                security_patch = androidInfo.version.securityPatch,
                ui_version = androidInfo.version.incremental,
                android_version = androidInfo.version.release,
                api_version = androidInfo.version.sdkInt.toString(),
                manufacture = androidInfo.manufacturer,
                user_type = "user",
                app_version_code = androidInfo.versionCode.toInt(),
                model = androidInfo.model,
                network_type = "wifi",
                brand = androidInfo.manufacturer
            )
        } else if (deviceInfoXState.isIos) {
            val iosInfo = deviceInfoXState.iosInfo
            DeviceInfoRequest(
                device_id = iosInfo.identifierForVendor,
                app_version = iosInfo.appVersion,
                security_patch = "",
                ui_version = iosInfo.appVersion,
                android_version = iosInfo.systemVersion,
                api_version = iosInfo.appShortVersion,
                manufacture = iosInfo.systemName,
                user_type = iosInfo.name,
                app_version_code = 0,
                model = iosInfo.model,
                network_type = "wifi",
                brand = iosInfo.localizedModel
            )
        } else {
            DeviceInfoRequest(
                device_id = "",
                app_version = "",
                security_patch = "",
                ui_version = "",
                android_version = "",
                api_version = "",
                manufacture = "",
                user_type = "",
                app_version_code = 0,
                model = "",
                network_type = "",
                brand = ""
            )
        }
    }




}

