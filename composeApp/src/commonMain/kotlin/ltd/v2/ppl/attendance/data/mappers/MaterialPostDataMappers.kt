package ltd.v2.ppl.attendance.data.mappers

import ltd.v2.ppl.attendance.data.response_model.MaterialPostDataModel
import ltd.v2.ppl.attendance.domain.model.MaterialPostDomainModel

fun MaterialPostDomainModel.toDataModel(): MaterialPostDataModel {
    return MaterialPostDataModel(
        mat_id = this.mat_id,
        qty = this.qty,
        campaign_id = this.campaign_id
    )
}

fun MaterialPostDataModel.toDomainModel(): MaterialPostDomainModel {
    return MaterialPostDomainModel(
        mat_id = this.mat_id,
        qty = this.qty,
        campaign_id = this.campaign_id
    )
}


