package ltd.v2.ppl.auth.data.response_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SignInResponse(
    @SerialName("status") val status: String,
    @SerialName("data") val data: SignInDataModel?
)

@Serializable
data class SignInDataModel(
    @SerialName("token")
    val token: String,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("username")
    val username: String? = null,

    @SerialName("user_type")
    val userType: String? = null,

    @SerialName("reset_stts")
    val resetStatus: Boolean? = null,

    @SerialName("agency_id")
    val agencyId: Int? = null,

    @SerialName("access_list")
    val accessList: List<Int>? = null,

    @SerialName("dailycheckObj")
    val dailyCheckStatus: DailyCheckStatusDataModel? = null,

    @SerialName("compatibility")
    val compatibility: Boolean? = null,

    @SerialName("authorize")
    val authorize: Boolean? = null,

    @SerialName("block")
    val block: Boolean? = null,

    @SerialName("org_name")
    val organizationName: String? = null,

    @SerialName("theme")
    val theme: ThemeDataModel? = null,

    @SerialName("org_settings")
    val organizationSettings: OrganizationSettingsDataModel? = null,

    @SerialName("app_version")
    val appVersion: AppVersionDataModel? = null
)

@Serializable
data class DailyCheckStatusDataModel(
    @SerialName("checkInStatus")
    val checkInStatus: Boolean? = null,

    @SerialName("checkInTime")
    val checkInTime: String? = null,

    @SerialName("checkOutStatus")
    val checkOutStatus: Boolean? = null,

    @SerialName("checkOutTime")
    val checkOutTime: String? = null,

    @SerialName("checkInDate")
    val checkInDate: String? = null,

    @SerialName("checkOutDate")
    val checkOutDate: String? = null
)

@Serializable
data class ThemeDataModel(
    @SerialName("priamry_color")
    val primaryColor: String? = null,

    @SerialName("org_logo")
    val organizationLogoUrl: String? = null
)

@Serializable
data class OrganizationSettingsDataModel(
    @SerialName("FF Module")
    val ffModule: FFModuleSettingsDataModel? = null,

    @SerialName("Attendance Module")
    val attendanceModule: AttendanceModuleSettingsDataModel? = null
)

@Serializable
data class FFModuleSettingsDataModel(
    @SerialName("employment_type")
    val employmentTypes: List<EmploymentTypeDataModel>? = null
)

@Serializable
data class EmploymentTypeDataModel(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("name")
    val name: String? = null
)

@Serializable
data class AttendanceModuleSettingsDataModel(
    @SerialName("Attendance")
    val attendance: AttendanceTimingDataModel? = null
)

@Serializable
data class AttendanceTimingDataModel(
    @SerialName("checkIn_time")
    val checkInTime: String? = null,

    @SerialName("checkOut_time")
    val checkOutTime: String? = null
)

@Serializable
data class AppVersionDataModel(
    @SerialName("force")
    val forceUpdate: Boolean? = null,

    @SerialName("version")
    val version: String? = null,

    @SerialName("url")
    val url: String? = null
)

