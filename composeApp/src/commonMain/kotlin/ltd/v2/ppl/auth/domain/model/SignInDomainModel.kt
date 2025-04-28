package ltd.v2.ppl.auth.domain.model

data class SignInDomainModel(
    val token: String,
    val id: Int? = null,
    val username: String? = null,
    val userType: String? = null,
    val resetStatus: Boolean? = null,
    val agencyId: Int? = null,
    val accessList: List<Int>? = null,
    val dailyCheckStatus: DailyCheckStatusDomain? = null,
    val compatibility: Boolean? = null,
    val authorize: Boolean? = null,
    val block: Boolean? = null,
    val organizationName: String? = null,
    val theme: ThemeDomain? = null,
    val organizationSettings: OrganizationSettingsDomain? = null,
    val appVersion: AppVersionDomain? = null
)

data class DailyCheckStatusDomain(
    val checkInStatus: Boolean? = null,
    val checkInTime: String? = null,
    val checkOutStatus: Boolean? = null,
    val checkOutTime: String? = null
)

data class ThemeDomain(
    val primaryColor: String? = null,
    val organizationLogoUrl: String? = null
)

data class OrganizationSettingsDomain(
    val ffModule: FFModuleSettingsDomain? = null,
    val attendanceModule: AttendanceModuleSettingsDomain? = null
)

data class FFModuleSettingsDomain(
    val employmentTypes: List<EmploymentTypeDomain>? = null
)

data class EmploymentTypeDomain(
    val id: Int? = null,
    val name: String? = null
)

data class AttendanceModuleSettingsDomain(
    val attendance: AttendanceTimingDomain? = null
)

data class AttendanceTimingDomain(
    val checkInTime: String? = null,
    val checkOutTime: String? = null
)

data class AppVersionDomain(
    val forceUpdate: Boolean? = null,
    val version: String? = null,
    val url: String? = null
)


