package ltd.v2.ppl.app

import androidx.compose.runtime.Composable
import ltd.v2.ppl.core.localization.Language
import ltd.v2.ppl.core.localization.Localization
import ltd.v2.ppl.core.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    AppTheme{
        val localization = koinInject<Localization>()

        localization.applyLanguage(Language.English.iso)

        AppNavigation()

    }
}