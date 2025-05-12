package ltd.v2.ppl.attendance.presentation.giveable

sealed interface GiveableIntent {
    data class IncrementQty(val campaignIndex: Int, val materialIndex: Int) : GiveableIntent
    data class DecrementQty(val campaignIndex: Int, val materialIndex: Int) : GiveableIntent
    data object OnNextClicked : GiveableIntent
}
