package ltd.v2.ppl.auth.data.response_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoModel(
    @SerialName("status")
    val status: String?,

    @SerialName("message")
    val message: String?,

    @SerialName("data")
    val data: List<UserDataModel>?
)

@Serializable
data class UserDataModel(
    @SerialName("name")
    val name: String?,

    @SerialName("user_image")
    val userImage: String?,

    @SerialName("designation")
    val designation: String?,

    @SerialName("locations")
    val locations: String? = null,

    @SerialName("agency_name")
    val agencyName: String?,

    @SerialName("org_name")
    val orgName: String? = null,

    @SerialName("geo_info")
    val geoFence: GeoFence?,

    @SerialName("role_id")
    val roleId: Int?,

    @SerialName("top_ff")
    val topFF: Boolean?,

    @SerialName("agency_id")
    val agencyId: Int?,

    @SerialName("access_list")
    val accessList: List<Int>?,

    @SerialName("geo_enable")
    val geoEnable: Int?
)

@Serializable
data class GeoFence(
    @SerialName("lat")
    val lat: Double?,

    @SerialName("long")
    val long: Double?,

    @SerialName("forced")
    val forced: Boolean?,

    @SerialName("radius")
    val radius: Int?,

    @SerialName("status")
    val status: Boolean?
)



