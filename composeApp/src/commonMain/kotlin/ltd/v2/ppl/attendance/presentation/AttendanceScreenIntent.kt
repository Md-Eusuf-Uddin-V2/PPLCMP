package ltd.v2.ppl.attendance.presentation

sealed class AttendanceScreenIntent {
    data object GoToNextPage : AttendanceScreenIntent()

}
