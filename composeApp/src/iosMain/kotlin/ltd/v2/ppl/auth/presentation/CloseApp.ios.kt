package ltd.v2.ppl.auth.presentation

import androidx.compose.runtime.Composable
import platform.posix.exit

@Composable
actual fun CloseApp() {
    exit(0)
}