package ltd.v2.ppl.attendance.data.response_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MaterialPostDataModel(
    @SerialName("mat_id") val mat_id: Int,
    @SerialName("qty") val qty: Int,
    @SerialName("campaign_id") val campaign_id: Int,
)

