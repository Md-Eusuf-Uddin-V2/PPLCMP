package ltd.v2.ppl.auth.data.mappers

import ltd.v2.ppl.auth.data.response_model.GeoFence
import ltd.v2.ppl.auth.data.response_model.UserDataModel
import ltd.v2.ppl.auth.domain.model.GeoFenceDomainModel
import ltd.v2.ppl.auth.domain.model.UserDataDomainModel


fun UserDataModel.toDomain(): UserDataDomainModel {
    return UserDataDomainModel(
        name = this.name,
        userImage = this.userImage,
        designation = this.designation,
        agencyName = this.agencyName,
        geoFence = this.geoFence?.toDomain(),
        roleId = this.roleId,
        topFF = this.topFF,
        agencyId = this.agencyId,
        accessList = this.accessList,
        geoEnable = this.geoEnable
    )
}

fun GeoFence?.toDomain(): GeoFenceDomainModel? {
    return this?.let {
        GeoFenceDomainModel(
            lat = it.lat,
            long = it.long,
            forced = it.forced,
            radius = it.radius,
            status = it.status
        )
    }
}
