package ltd.v2.ppl.auth.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlin.system.exitProcess


@Composable
actual fun CloseApp() {
    if(LocalContext.current is Activity){
        (LocalContext.current as Activity).finish()
    }
}