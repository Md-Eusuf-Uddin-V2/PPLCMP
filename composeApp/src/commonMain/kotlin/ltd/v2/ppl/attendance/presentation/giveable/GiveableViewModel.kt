package ltd.v2.ppl.attendance.presentation.giveable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ltd.v2.ppl.attendance.domain.model.GiveableDomain
import ltd.v2.ppl.attendance.domain.model.MaterialPostDomainModel
import ltd.v2.ppl.attendance.domain.use_case.PostMaterialsData
import ltd.v2.ppl.core.data_source.app_pref.AppPreference
import ltd.v2.ppl.core.domain.Result

class GiveableViewModel(
    private val appPref: AppPreference,
    private val postMaterialsData: PostMaterialsData
) : ViewModel() {

    private val _state = MutableStateFlow(GiveableState())
    val state: StateFlow<GiveableState> = _state

    private val _oneTimeState = MutableStateFlow(GiveableState())
    val oneTimeState: StateFlow<GiveableState> = _oneTimeState

    fun onIntent(intent: GiveableIntent) {
        when (intent) {
            is GiveableIntent.IncrementQty -> updateQty(
                intent.campaignIndex,
                intent.materialIndex,
                +1
            )

            is GiveableIntent.DecrementQty -> updateQty(
                intent.campaignIndex,
                intent.materialIndex,
                -1
            )

            is GiveableIntent.OnNextClicked -> onNextCLicked()
        }
    }

    private fun updateQty(campaignIndex: Int, materialIndex: Int, delta: Int) {
        _state.update { state ->
            val updatedCampaigns = state.materials.mapIndexed { cIndex, campaign ->
                if (cIndex == campaignIndex) {
                    val updatedMaterials = campaign.materials?.mapIndexed { mIndex, material ->
                        if (mIndex == materialIndex) {
                            val newQty = (material.acceptedQty + delta).coerceIn(0, material.qty)
                            material.copy(acceptedQty = newQty)
                        } else material
                    }
                    campaign.copy(materials = updatedMaterials)
                } else campaign
            }
            state.copy(materials = updatedCampaigns)
        }
    }

    fun loadGiveableData(giveableList: List<GiveableDomain>) {
        _state.update { it.copy(materials = giveableList) }
    }


    private fun onNextCLicked() {
        viewModelScope.launch {
            val acceptedList: List<MaterialPostDomainModel> =
                appPref.getAssetsPostData().orEmpty() +
                        _state.value.materials.flatMap { giveable ->
                            giveable.materials?.map { material ->
                                MaterialPostDomainModel(
                                    mat_id = material.id ?: 0,
                                    qty = material.acceptedQty,
                                    campaign_id = giveable.campaignId ?: 0
                                )
                            }.orEmpty()
                        }

            appPref.storeAssetsPostData(Json.encodeToString(""))
            _state.update { it.copy(isLoading = true) }
            when (val result = postMaterialsData(acceptedList, appPref.getLoginData().token)) {

                is Result.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _oneTimeState.emit(
                        GiveableState(message = "Materials Accepted Successfully!")
                    )
                    _oneTimeState.update { it.copy(isNextEnabled = true) }
                }

                is Result.Error -> {
                    if (result.error.message == "Already Accepted!!") {
                        _oneTimeState.emit(
                            GiveableState(
                                message = result.error.message
                            )
                        )
                        _oneTimeState.update { it.copy(isNextEnabled = true) }
                    } else {
                        _state.update { it.copy(error = result.error.message) }
                    }
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }

    }


}
