package ltd.v2.ppl.attendance.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AttendanceScreenRoot(
    viewModel: AttendanceScreenViewModel,
) {

    Box(Modifier.fillMaxSize()) {
        Text("Attendance Screen", modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
    }

}