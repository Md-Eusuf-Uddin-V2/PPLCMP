package ltd.v2.ppl.attendance.domain

data class MaterialModel(
    val status: String?,
    val message: String?,
    val data: MaterialData?
)

data class MaterialData(
    val giveable: List<Giveable>?,
    val nonGiveable: List<NonGiveable>?
)

data class Giveable(
    val campaignId: Int?,
    val campaignName: String?,
    val materials: List<Material>?
)

data class Material(
    val id: Int?,
    val name: String?,
    val campaignName: String?,
    val qty: Int?,
    val type: Int?,
    val typeName: Int?, // This should be a String? if it's actually a name
    val campaignId: Int?,
    val accepted: Boolean?
)

data class NonGiveable(
    val id: Int?,
    val name: String?,
    val qty: Int?,
    val type: Int?,
    val typeName: Int?, // This should be a String? if it's a name
    val campaignId: Int?,
    val campaignName: String?
)


