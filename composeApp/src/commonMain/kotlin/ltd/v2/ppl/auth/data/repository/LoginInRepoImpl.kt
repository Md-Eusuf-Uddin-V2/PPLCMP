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
import ltd.v2.ppl.auth.domain.model.SignInDomainModel
import ltd.v2.ppl.auth.domain.model.UserDataDomainModel
import ltd.v2.ppl.auth.domain.repository.LogInRepo
import ltd.v2.ppl.common_utils.utils.convertMapToJsonObject
import ltd.v2.ppl.core.app_url.AppUrl
import ltd.v2.ppl.core.data_source.app_pref.AppPreference
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.LocalError
import ltd.v2.ppl.core.domain.RemoteError
import ltd.v2.ppl.core.domain.Result

class LoginInRepoImpl(
    private val httpClient: HttpClient,
    private val appPref: AppPreference
) : LogInRepo {

    override suspend fun getSignInDataResponse(signInMap: Map<String, Any>): Result<SignInDomainModel, DataError> {
        return try {

            val requestObject = convertMapToJsonObject(signInMap)

            val response = httpClient.post {
                url("${AppUrl.BASE_URL}${AppUrl.Auth.SIGN_IN_URL}")
                contentType(ContentType.Application.Json)
                setBody(requestObject)
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

            val response = httpClient.get {
                url("${AppUrl.BASE_URL}${AppUrl.Auth.USER_INFO_URL}")
                header("Authorization", "Bearer $token")
            }

            if (response.status.isSuccess()) {
                val userInfoModel: UserInfoModel = response.body()
                val userDataList = userInfoModel.data

                if (!userDataList.isNullOrEmpty()) {
                    appPref.setUserInfoData(Json.encodeToString(userDataList[0]))
                    Result.Success(userDataList[0].toDomain())
                } else {
                    Result.Error(LocalError("No User Data Found"))
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
                    appPref.setCampaignData(Json.encodeToString(campaignList))
                    Result.Success(campaignList.map { it.toDomain() })
                } else {
                    Result.Error(LocalError("No campaign assigned!"))
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


}

