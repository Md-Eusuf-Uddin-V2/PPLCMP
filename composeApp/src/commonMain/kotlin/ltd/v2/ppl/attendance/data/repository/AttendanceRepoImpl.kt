package ltd.v2.ppl.attendance.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import ltd.v2.ppl.attendance.data.mappers.toDataModel
import ltd.v2.ppl.attendance.data.mappers.toDomain
import ltd.v2.ppl.attendance.data.response_model.MaterialModel
import ltd.v2.ppl.attendance.data.response_model.MaterialPostDataModel
import ltd.v2.ppl.attendance.domain.model.MaterialDataDomain
import ltd.v2.ppl.attendance.domain.model.MaterialPostDomainModel
import ltd.v2.ppl.attendance.domain.repositories.AttendanceRepo
import ltd.v2.ppl.common_utils.utils.convertMapToJsonObject
import ltd.v2.ppl.core.app_url.AppUrl
import ltd.v2.ppl.core.data_source.app_pref.AppPreference
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.LocalError
import ltd.v2.ppl.core.domain.RemoteError
import ltd.v2.ppl.core.domain.Result

class AttendanceRepoImpl(
    private val httpClient: HttpClient,
    private val appPref: AppPreference,
) : AttendanceRepo {
    override suspend fun getMaterials(token: String): Result<MaterialDataDomain, DataError> {
        return try {

            val response = httpClient.get {
                url("${AppUrl.BASE_URL}${AppUrl.Attendance.MATERIALS_URL}")
                header("Authorization", "Bearer $token")
            }

            if (response.status.isSuccess()) {
                val materialModel: MaterialModel = response.body()
                val materialData = materialModel.data

                if (materialData != null) {
                    Result.Success(materialData.toDomain())
                } else {
                    Result.Error(LocalError("No material data Found"))
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

    override suspend fun postMaterials(
        materialPostList: List<MaterialPostDomainModel>,
        token: String
    ): Result<Boolean, DataError> {
        return try {

            val materialPostListData : List<MaterialPostDataModel> = materialPostList.map { it.toDataModel() }
            val response = httpClient.post {
                url("${AppUrl.BASE_URL}${AppUrl.Attendance.POST_MATERIALS_URL}")
                setBody(materialPostListData)
                header("Authorization", "Bearer $token")
            }

            if (response.status.isSuccess()) {
                Result.Success(true)

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