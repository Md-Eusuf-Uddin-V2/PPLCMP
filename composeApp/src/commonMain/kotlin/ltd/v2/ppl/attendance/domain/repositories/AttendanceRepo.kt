package ltd.v2.ppl.attendance.domain.repositories


import kotlinx.serialization.json.JsonObject
import ltd.v2.ppl.attendance.domain.model.MaterialDataDomain
import ltd.v2.ppl.attendance.domain.model.MaterialPostDomainModel
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

interface AttendanceRepo {
    suspend fun getMaterials(token: String): Result<MaterialDataDomain, DataError>
    suspend fun postMaterials(materialPostList: List<MaterialPostDomainModel>, token: String): Result<Boolean, DataError>
}