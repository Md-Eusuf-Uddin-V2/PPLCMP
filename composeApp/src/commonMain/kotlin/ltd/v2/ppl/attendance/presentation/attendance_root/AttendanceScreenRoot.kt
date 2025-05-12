package ltd.v2.ppl.attendance.presentation.attendance_root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import multiplatform.network.cmptoast.showToast

@Composable
fun AttendanceScreenRoot(
    viewModel: AttendanceScreenViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.oneTimeState.collect { oneTimeState ->
            when {
                oneTimeState.noInternetAvailable -> showToast("No Internet Available")
                oneTimeState.error != null -> showToast(state.error ?: "")
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {

            AttendanceFlowPager(state = state, viewModel = viewModel, onBackClick = onBackClick)
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceFlowPager(
    state: AttendanceScreenState,
    viewModel: AttendanceScreenViewModel,
    onBackClick: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.attendanceFlowList.size }
    )

    LaunchedEffect(pagerState.settledPage) {
        viewModel.setPage(pagerState.settledPage)
    }

    LaunchedEffect(state.currentPage) {
        if (pagerState.settledPage != state.currentPage) {
            pagerState.animateScrollToPage(state.currentPage)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance") },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) // Use scaffold's inner padding
                .padding(16.dp)
        ) { page ->
            state.attendanceFlowList[page]()
        }
    }
}


