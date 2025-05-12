package ltd.v2.ppl.attendance.presentation.giveable

import ltd.v2.ppl.attendance.domain.model.GiveableDomain

data class GiveableState(
    val isLoading: Boolean = false,
    val materials: List<GiveableDomain> = emptyList(),
    val noInternetAvailable: Boolean = false,
    val message: String? = null,
    val error: String? = null,
    val isNextEnabled: Boolean = false
)
