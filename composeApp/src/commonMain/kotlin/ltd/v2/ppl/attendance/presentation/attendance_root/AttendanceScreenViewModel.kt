package ltd.v2.ppl.attendance.presentation.attendance_root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ltd.v2.ppl.attendance.domain.use_case.GetMaterialsData
import ltd.v2.ppl.attendance.presentation.asset.AssetCollectionCompose
import ltd.v2.ppl.attendance.presentation.components.CheckInCompose
import ltd.v2.ppl.attendance.presentation.checkin_image_capture.CheckInImageCaptureCompose
import ltd.v2.ppl.attendance.presentation.components.CheckOutCompose
import ltd.v2.ppl.attendance.presentation.giveable.GiveAbleCollectionCompose
import ltd.v2.ppl.common_utils.constants.AppConstant
import ltd.v2.ppl.core.data_source.app_pref.AppPreference
import ltd.v2.ppl.core.domain.Result

class AttendanceScreenViewModel(
    private val getMaterialsData: GetMaterialsData,
    private val appPref: AppPreference,
    private val connectivity: Connectivity,
) : ViewModel() {

    private val _state = MutableStateFlow(AttendanceScreenState())
    val state: StateFlow<AttendanceScreenState> = _state.asStateFlow()

    private val _oneTimeState = MutableSharedFlow<AttendanceScreenState>()
    val oneTimeState: SharedFlow<AttendanceScreenState> = _oneTimeState

    init {
        getMaterials()
    }


    private fun onIntent(intent: AttendanceScreenIntent) {
        when (intent) {
            is AttendanceScreenIntent.GoToNextPage -> {
                goToNextPage()

            }
        }
    }

    private fun getMaterials() {
        viewModelScope.launch {
            if (!connectivity.status().isConnected) {
                _oneTimeState.emit(AttendanceScreenState(noInternetAvailable = true))
                return@launch
            }

            _state.update { it.copy(isLoading = true) }
            when (val result = getMaterialsData(appPref.getLoginData().token)) {

                is Result.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    if (AppConstant.accessList.contains(AppConstant.ASSET_COLLECTION) && result.data.nonGiveable?.size != 0) {
                        _state.update {
                            it.copy(attendanceFlowList = listOf({
                                AssetCollectionCompose(
                                    title = "Asset Collection",
                                    assetsList = result.data.nonGiveable ?: emptyList(),
                                    onNext = { onIntent(AttendanceScreenIntent.GoToNextPage) })
                            }))
                        }
                    }

                    if (AppConstant.accessList.contains(AppConstant.GIVEABLE_COLLECTION) && result.data.giveable?.size != 0) {
                        _state.update {
                            it.copy(attendanceFlowList = it.attendanceFlowList.plus {
                                GiveAbleCollectionCompose(
                                    title = "Giveable Collection",
                                    initialMaterials = result.data.giveable ?: emptyList(),
                                    onNext = { onIntent(AttendanceScreenIntent.GoToNextPage) }
                                )
                            })
                        }
                    }

                    if (AppConstant.accessList.contains(AppConstant.ATTENDANCE_PHOTO)) {
                        if (appPref.getAttendanceInfo().checkInStatus != null && !appPref.getAttendanceInfo().checkOutStatus!!) {
                            if (AppConstant.accessList.contains(AppConstant.CHECK_IN_PHOTO_CAPTURE) || AppConstant.accessList.contains(
                                    AppConstant.CHECK_IN_GROUP_PHOTO_CAPTURE
                                )
                            ) {
                                _state.update {
                                    it.copy(attendanceFlowList = it.attendanceFlowList + {
                                        CheckInImageCaptureCompose(onNext = {
                                            onIntent(AttendanceScreenIntent.GoToNextPage)
                                        })
                                    })
                                }
                            }
                        } else {
                            if (AppConstant.accessList.contains(AppConstant.CHECK_OUT_PHOTO_CAPTURE) || AppConstant.accessList.contains(
                                    AppConstant.CHECK_OUT_GROUP_PHOTO_CAPTURE
                                )
                            ) {
                                _state.update {
                                    it.copy(attendanceFlowList = it.attendanceFlowList + {
                                        CheckInImageCaptureCompose(onNext = {
                                            onIntent(AttendanceScreenIntent.GoToNextPage)
                                        })
                                    })
                                }
                            }

                        }
                    }

                    if (AppConstant.accessList.contains(AppConstant.CHECK_IN_OUT)) {
                        if (appPref.getAttendanceInfo().checkInStatus != null && !appPref.getAttendanceInfo().checkInStatus!!) {
                            _state.update {
                                it.copy(attendanceFlowList = it.attendanceFlowList + {
                                    CheckInCompose(
                                        onNext = {

                                        })
                                })
                            }
                        }

                    } else {
                        _state.update {
                            it.copy(attendanceFlowList = it.attendanceFlowList + {
                                CheckOutCompose(
                                    onNext = {

                                    })
                            })
                        }
                    }

                }

                is Result.Error -> {
                    println("Error result is ${result.error.message}")
                    _oneTimeState.emit(AttendanceScreenState(error = result.error.message))
                }
            }

        }
    }

    private fun goToNextPage() {
        _state.update {
            val nextPage = (it.currentPage + 1).coerceAtMost(it.attendanceFlowList.lastIndex)
            it.copy(currentPage = nextPage)
        }
    }

    fun setPage(index: Int) {
        _state.update {
            val maxIndex = it.attendanceFlowList.lastIndex
            it.copy(
                currentPage = if (maxIndex >= 0) index.coerceIn(0..maxIndex) else 0
            )
        }
    }

    private fun goToPreviousPage() {
        _state.update {
            val prevPage = (it.currentPage - 1).coerceAtLeast(0)
            it.copy(currentPage = prevPage)
        }
    }


}