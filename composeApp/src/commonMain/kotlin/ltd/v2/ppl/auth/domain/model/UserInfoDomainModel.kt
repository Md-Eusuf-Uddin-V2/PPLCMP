package ltd.v2.ppl.auth.domain.model


data class UserDataDomainModel(
    val name: String?,
    val userImage: String?,
    val designation: String?,
    val locations: String? = null,
    val agencyName: String?,
    val orgName: String? = null,
    val geoFence: GeoFenceDomainModel?,
    val roleId: Int?,
    val topFF: Boolean?,
    val agencyId: Int?,
    val accessList: List<Int>?,
    val geoEnable: Int?
)

data class GeoFenceDomainModel(
    val lat: Double?,
    val long: Double?,
    val forced: Boolean?,
    val radius: Int?,
    val status: Boolean?
)