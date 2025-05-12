package ltd.v2.ppl.attendance.presentation.asset

sealed interface AssetsIntent {
    data class IncrementQty(val assetId: Int) : AssetsIntent
    data class DecrementQty(val assetId: Int) : AssetsIntent
    data object OnNextClicked : AssetsIntent
}
