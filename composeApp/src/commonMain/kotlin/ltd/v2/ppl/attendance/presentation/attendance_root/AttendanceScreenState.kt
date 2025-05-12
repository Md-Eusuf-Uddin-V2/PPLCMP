package ltd.v2.ppl.attendance.presentation.attendance_root

import androidx.compose.runtime.Composable
import ltd.v2.ppl.attendance.domain.model.MaterialDataDomain


data class AttendanceScreenState (
    val materials: MaterialDataDomain? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val noInternetAvailable: Boolean = false,
    val attendanceFlowList: List<@Composable () -> Unit> = emptyList(),
    val currentPage: Int = 0
)