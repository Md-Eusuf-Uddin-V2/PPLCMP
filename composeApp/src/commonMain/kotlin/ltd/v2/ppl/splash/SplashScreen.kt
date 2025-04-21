package ltd.v2.ppl.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import pplcmp.composeapp.generated.resources.Res
import pplcmp.composeapp.generated.resources.app_logo

@Composable
fun SplashScreenRoot(
    viewModel: SplashViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // Handle navigation when delay completes
    LaunchedEffect(state.shouldNavigate) {
        if (state.shouldNavigate) {
            onNavigateToLogin()
        }
    }

    // Pure UI component
    SplashScreen()
}


@Composable
fun SplashScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween // Key change for weight distribution
            ) {
                // Empty space at top (weight 1)
               // Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(120.dp))

                // Logo section (centered)
                Image(
                    painter = painterResource(Res.drawable.app_logo),
                    contentDescription = "Splash Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Progress indicator section (weight 1)
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}

