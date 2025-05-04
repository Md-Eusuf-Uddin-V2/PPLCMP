package ltd.v2.ppl.auth.data.mappers

import ltd.v2.ppl.auth.data.response_model.AppVersionDataModel
import ltd.v2.ppl.auth.data.response_model.AttendanceModuleSettingsDataModel
import ltd.v2.ppl.auth.data.response_model.AttendanceTimingDataModel
import ltd.v2.ppl.auth.data.response_model.DailyCheckStatusDataModel
import ltd.v2.ppl.auth.data.response_model.EmploymentTypeDataModel
import ltd.v2.ppl.auth.data.response_model.FFModuleSettingsDataModel
import ltd.v2.ppl.auth.data.response_model.OrganizationSettingsDataModel
import ltd.v2.ppl.auth.data.response_model.SignInDataModel
import ltd.v2.ppl.auth.data.response_model.ThemeDataModel
import ltd.v2.ppl.auth.domain.model.AppVersionDomain
import ltd.v2.ppl.auth.domain.model.AttendanceModuleSettingsDomain
import ltd.v2.ppl.auth.domain.model.AttendanceTimingDomain
import ltd.v2.ppl.auth.domain.model.DailyCheckStatusDomain
import ltd.v2.ppl.auth.domain.model.EmploymentTypeDomain
import ltd.v2.ppl.auth.domain.model.FFModuleSettingsDomain
import ltd.v2.ppl.auth.domain.model.OrganizationSettingsDomain
import ltd.v2.ppl.auth.domain.model.SignInDomainModel
import ltd.v2.ppl.auth.domain.model.ThemeDomain

fun SignInDataModel.toDomain(): SignInDomainModel {
    return SignInDomainModel(
        token = token,
        id = id,
        username = username,
        userType = userType,
        resetStatus = resetStatus,
        agencyId = agencyId,
        accessList = accessList,
        dailyCheckStatus = dailyCheckStatus?.toDomain(),
        compatibility = compatibility,
        authorize = authorize,
        block = block,
        organizationName = organizationName,
        theme = theme?.toDomain(),
        organizationSettings = organizationSettings?.toDomain(),
        appVersion = appVersion?.toDomain()
    )
}

 fun DailyCheckStatusDataModel?.toDomain(): DailyCheckStatusDomain {
    return DailyCheckStatusDomain(
        checkInStatus = this?.checkInStatus,
        checkInTime = this?.checkInTime,
        checkInDate = this?.checkInDate,
        checkOutStatus = this?.checkOutStatus,
        checkOutTime = this?.checkOutTime,
        checkOutDate = this?.checkOutDate
    )
}

 fun ThemeDataModel?.toDomain(): ThemeDomain {
    return ThemeDomain(
        primaryColor = this?.primaryColor,
        organizationLogoUrl = this?.organizationLogoUrl
    )
}

 fun OrganizationSettingsDataModel?.toDomain(): OrganizationSettingsDomain {
    return OrganizationSettingsDomain(
        ffModule = this?.ffModule?.toDomain(),
        attendanceModule = this?.attendanceModule?.toDomain()
    )
}

 fun FFModuleSettingsDataModel?.toDomain(): FFModuleSettingsDomain {
    return FFModuleSettingsDomain(
        employmentTypes = this?.employmentTypes?.map { it.toDomain() }
    )
}

 fun EmploymentTypeDataModel?.toDomain(): EmploymentTypeDomain {
    return EmploymentTypeDomain(
        id = this?.id,
        name = this?.name
    )
}

 fun AttendanceModuleSettingsDataModel?.toDomain(): AttendanceModuleSettingsDomain {
    return AttendanceModuleSettingsDomain(
        attendance = this?.attendance?.toDomain()
    )
}

 fun AttendanceTimingDataModel?.toDomain(): AttendanceTimingDomain {
    return AttendanceTimingDomain(
        checkInTime = this?.checkInTime,
        checkOutTime = this?.checkOutTime
    )
}

 fun AppVersionDataModel?.toDomain(): AppVersionDomain {
    return AppVersionDomain(
        forceUpdate = this?.forceUpdate,
        version = this?.version,
        url = this?.url
    )
}

