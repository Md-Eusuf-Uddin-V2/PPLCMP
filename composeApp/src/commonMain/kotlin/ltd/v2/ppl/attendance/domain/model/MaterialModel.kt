package ltd.v2.ppl.attendance.domain.model

data class MaterialDomainModel(
    val status: String?,
    val message: String?,
    val data: MaterialDataDomain?
)

data class MaterialDataDomain(
    val giveable: List<GiveableDomain>?,
    val nonGiveable: List<NonGiveableDomain>?
)

data class GiveableDomain(
    val campaignId: Int?,
    val campaignName: String?,
    val materials: List<MaterialItemDomain>?
)

data class MaterialItemDomain(
    val id: Int?,
    val name: String?,
    val campaignName: String?,
    val qty: Int?,
    val acceptedQty: Int = 0,
    val type: Int?,
    val typeName: Int?,
    val campaignId: Int?,
    val accepted: Boolean?
)

data class NonGiveableDomain(
    val id: Int?,
    val name: String?,
    val qty: Int?,
    val acceptedQty: Int = 0,
    val type: Int?,
    val typeName: Int?,
    val campaignId: Int?,
    val campaignName: String?
)




