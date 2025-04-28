package ltd.v2.ppl.auth.data.mappers

import ltd.v2.ppl.auth.data.response_model.CampaignListModel
import ltd.v2.ppl.auth.data.response_model.CampaignsModel
import ltd.v2.ppl.auth.data.response_model.JointCallTarget
import ltd.v2.ppl.auth.domain.model.CampaignListDomainModel
import ltd.v2.ppl.auth.domain.model.CampaignsDomainModel
import ltd.v2.ppl.auth.domain.model.JointCallTargetDomainModel

fun CampaignsModel.toDomain(): CampaignsDomainModel {
    return CampaignsDomainModel(
        status = this.status,
        message = this.message,
        campaignList = this.campaignList?.map { it.toDomain() }
    )
}

fun CampaignListModel.toDomain(): CampaignListDomainModel {
    return CampaignListDomainModel(
        id = this.id,
        name = this.name,
        jointCall = this.jointCall,
        jointCallTarget = this.jointCallTarget?.toDomain()
    )
}

fun JointCallTarget.toDomain(): JointCallTargetDomainModel {
    return JointCallTargetDomainModel(
        perFFjointCallCount = this.perFFjointCallCount,
        totalJointCallTarget = this.totalJointCallTarget,
        totalTargetValidation = this.totalTargetValidation
    )
}
