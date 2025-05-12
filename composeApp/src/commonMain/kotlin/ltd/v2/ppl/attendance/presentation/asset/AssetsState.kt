package ltd.v2.ppl.attendance.presentation.asset

import ltd.v2.ppl.attendance.domain.model.NonGiveableDomain

data class AssetsState(
    val assetsList: List<NonGiveableDomain> = emptyList(),
    val noInternetAvailable: Boolean = false,
    val isNextEnabled: Boolean = false,
    val isLoading: Boolean = false
)
