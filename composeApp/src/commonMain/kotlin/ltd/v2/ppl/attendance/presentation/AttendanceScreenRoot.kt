package ltd.v2.ppl.attendance.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.CircularProgressIndicator
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

            AttendanceFlowPager(state = state, viewModel = viewModel)
        }


    }
}


@Composable
fun AttendanceFlowPager(state: AttendanceScreenState, viewModel: AttendanceScreenViewModel) {
    val pagerState = androidx.compose.foundation.pager.rememberPagerState(
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

    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) { page ->
        state.attendanceFlowList[page]()
    }
}

