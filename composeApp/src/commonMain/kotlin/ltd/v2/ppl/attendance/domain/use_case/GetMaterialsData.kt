package ltd.v2.ppl.attendance.domain.use_case

import ltd.v2.ppl.attendance.domain.model.MaterialDataDomain
import ltd.v2.ppl.attendance.domain.repositories.AttendanceRepo
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

class GetMaterialsData(private val attendanceRepo: AttendanceRepo) {

    suspend  operator fun invoke(token : String) : Result<MaterialDataDomain, DataError> {
        return  attendanceRepo.getMaterials(token = token)
    }
}


