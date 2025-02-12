package ltd.v2.ppl.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ltd.v2.ppl.core.localization.Language
import ltd.v2.ppl.core.localization.Localization
import ltd.v2.ppl.core.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import pplcmp.composeapp.generated.resources.Res
import pplcmp.composeapp.generated.resources.app_name

@Composable
@Preview
fun App() {
    AppTheme{
        val localization = koinInject<Localization>()
       /* val languageIso by rememberStringSetting(
            key = "savedLanguageIso",
            defaultValue = Language.Bangla.iso
        )*/


        localization.applyLanguage(Language.English.iso)

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = stringResource(resource = Res.string.app_name))
        }

    }
}