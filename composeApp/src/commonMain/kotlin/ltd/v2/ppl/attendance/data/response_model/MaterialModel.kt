package ltd.v2.ppl.attendance.data.response_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MaterialModel(
    @SerialName("status") val status: String?,
    @SerialName("message") val message: String?,
    @SerialName("data") val data: MaterialData?
)

@Serializable
data class MaterialData(
    @SerialName("giveable") val giveable: List<Giveable>?,
    @SerialName("non_giveable") val nonGiveable: List<NonGiveable>?
)

@Serializable
data class Giveable(
    @SerialName("campaign_id") val campaignId: Int?,
    @SerialName("campaign_name") val campaignName: String?,
    @SerialName("materials") val materials: List<Material>?
)

@Serializable
data class Material(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("campaign_name") val campaignName: String?,
    @SerialName("qty") val qty: Int?,
    @SerialName("type") val type: Int?,
    @SerialName("type_name") val typeName: Int?,
    @SerialName("campaign_id") val campaignId: Int?,
    @SerialName("accepted") val accepted: Boolean? = false
)

@Serializable
data class NonGiveable(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("qty") val qty: Int?,
    @SerialName("type") val type: Int?,
    @SerialName("type_name") val typeName: Int?,
    @SerialName("campaign_id") val campaignId: Int?,
    @SerialName("campaign_name") val campaignName: String?
)

