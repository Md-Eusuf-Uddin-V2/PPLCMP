package ltd.v2.ppl.attendance.presentation.asset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ltd.v2.ppl.attendance.data.mappers.toDataModel
import ltd.v2.ppl.attendance.domain.model.MaterialPostDomainModel
import ltd.v2.ppl.attendance.domain.model.NonGiveableDomain
import ltd.v2.ppl.core.data_source.app_pref.AppPreference

class AssetsViewModel(
    private val appPref: AppPreference
) : ViewModel() {

    private val _state = MutableStateFlow(AssetsState())
    val state: StateFlow<AssetsState> = _state

    private val _oneTimeState = MutableStateFlow(AssetsState())
    val oneTimeState: StateFlow<AssetsState> = _oneTimeState

    fun onIntent(intent: AssetsIntent) {
        when (intent) {
            is AssetsIntent.IncrementQty -> updateQty(intent.assetId, +1)
            is AssetsIntent.DecrementQty -> updateQty(intent.assetId, -1)
            is AssetsIntent.OnNextClicked -> onNextClicked()
        }
    }

    private fun updateQty(assetId: Int?, delta: Int) {
        if (assetId == null) return

        _state.update { state ->
            val updatedList = state.assetsList.map { asset ->
                if (asset.id == assetId) {
                    val newQty = (asset.acceptedQty + delta).coerceIn(0, asset.qty ?: 0)
                    asset.copy(acceptedQty = newQty)
                } else asset
            }
            state.copy(assetsList = updatedList)
        }
    }

    fun loadAssetsListData(assetsList: List<NonGiveableDomain>) {
        _state.update { it.copy(assetsList = assetsList) }
    }

    private fun onNextClicked() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val acceptedList = _state.value.assetsList
                .filter { it.acceptedQty > 0 }
                .map {
                    MaterialPostDomainModel(
                        mat_id = it.id ?: 0,
                        qty = it.acceptedQty,
                        campaign_id = it.campaignId ?: 0
                    )
                }

            appPref.storeAssetsPostData(Json.encodeToString(acceptedList.map { it.toDataModel() }))
            _state.update { it.copy(isLoading = false) }

            _oneTimeState.update { it.copy(isNextEnabled = true) }

        }
    }
}
