package ltd.v2.ppl.attendance.presentation.attendance_root

sealed class AttendanceScreenIntent {
    data object GoToNextPage : AttendanceScreenIntent()

}
