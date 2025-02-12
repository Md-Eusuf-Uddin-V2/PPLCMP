package ltd.v2.ppl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ltd.v2.ppl.app.App
import ltd.v2.ppl.core.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

