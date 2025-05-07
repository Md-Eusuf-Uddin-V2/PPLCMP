package ltd.v2.ppl.attendance.domain.repositories


import ltd.v2.ppl.attendance.domain.model.MaterialDataDomain
import ltd.v2.ppl.attendance.domain.model.MaterialDomainModel
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

interface AttendanceRepo {
    suspend fun getMaterials(token: String): Result<MaterialDataDomain, DataError>
}