package ltd.v2.ppl.attendance.domain.use_case

import ltd.v2.ppl.attendance.domain.model.MaterialPostDomainModel
import ltd.v2.ppl.attendance.domain.repositories.AttendanceRepo
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

class PostMaterialsData(private val attendanceRepo: AttendanceRepo) {
    suspend  operator fun invoke(materialPostList : List<MaterialPostDomainModel>,token : String) : Result<Boolean, DataError> {
        return  attendanceRepo.postMaterials(materialPostList = materialPostList,token = token)
    }
}