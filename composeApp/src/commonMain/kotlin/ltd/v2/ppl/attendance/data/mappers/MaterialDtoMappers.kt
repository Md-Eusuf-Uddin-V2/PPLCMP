package ltd.v2.ppl.attendance.data.mappers

import ltd.v2.ppl.attendance.data.response_model.Giveable
import ltd.v2.ppl.attendance.data.response_model.Material
import ltd.v2.ppl.attendance.data.response_model.MaterialData
import ltd.v2.ppl.attendance.data.response_model.NonGiveable
import ltd.v2.ppl.attendance.domain.model.GiveableDomain
import ltd.v2.ppl.attendance.domain.model.MaterialDataDomain
import ltd.v2.ppl.attendance.domain.model.MaterialItemDomain
import ltd.v2.ppl.attendance.domain.model.NonGiveableDomain


fun MaterialData.toDomain(): MaterialDataDomain = MaterialDataDomain(
    giveable = giveable?.map { it.toDomain() } ?: emptyList(),
    nonGiveable = nonGiveable?.map { it.toDomain() } ?: emptyList()
)

fun Giveable.toDomain(): GiveableDomain = GiveableDomain(
    campaignId = campaignId ?: 0,
    campaignName = campaignName.orEmpty(),
    materials = materials?.map { it.toDomain() } ?: emptyList()
)

fun Material.toDomain(): MaterialItemDomain = MaterialItemDomain(
    id = id ?: 0,
    name = name.orEmpty(),
    campaignName = campaignName.orEmpty(),
    qty = qty ?: 0,
    acceptedQty = this.qty ?: 0,
    type = type ?: 0,
    typeName = typeName,
    campaignId = campaignId ?: 0,
    accepted = accepted ?: false
)

fun NonGiveable.toDomain(): NonGiveableDomain = NonGiveableDomain(
    id = id ?: 0,
    name = name.orEmpty(),
    qty = qty ?: 0,
    type = type ?: 0,
    typeName = typeName,
    campaignId = campaignId ?: 0,
    campaignName = campaignName.orEmpty()
)
