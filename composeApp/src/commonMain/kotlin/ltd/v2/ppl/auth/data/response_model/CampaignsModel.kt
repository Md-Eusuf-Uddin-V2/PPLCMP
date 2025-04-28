package ltd.v2.ppl.auth.data.response_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CampaignsModel(
    @SerialName("status")
    val status: String?,

    @SerialName("message")
    val message: String?,

    @SerialName("data")
    val campaignList: List<CampaignListModel>?
)

@Serializable
data class CampaignListModel(
    @SerialName("id")
    val id: Int?,

    @SerialName("name")
    val name: String?,

    @SerialName("joint_call")
    val jointCall: Boolean? = null,

    @SerialName("joint_call_target")
    val jointCallTarget: JointCallTarget? = null
)

@Serializable
data class JointCallTarget(
    @SerialName("perFFjointCallCount")
    val perFFjointCallCount: Int?,

    @SerialName("totalJointCallTarget")
    val totalJointCallTarget: Int?,

    @SerialName("totalTargetValidation")
    val totalTargetValidation: Boolean?
)

