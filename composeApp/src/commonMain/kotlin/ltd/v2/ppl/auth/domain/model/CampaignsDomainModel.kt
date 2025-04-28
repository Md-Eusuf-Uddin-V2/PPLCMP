package ltd.v2.ppl.auth.domain.model

data class CampaignsDomainModel(
    val status: String?,
    val message: String?,
    val campaignList: List<CampaignListDomainModel>?
)

data class CampaignListDomainModel(
    val id: Int?,
    val name: String?,
    val jointCall: Boolean?,
    val jointCallTarget: JointCallTargetDomainModel?
)

data class JointCallTargetDomainModel(
    val perFFjointCallCount: Int?,
    val totalJointCallTarget: Int?,
    val totalTargetValidation: Boolean?
)
