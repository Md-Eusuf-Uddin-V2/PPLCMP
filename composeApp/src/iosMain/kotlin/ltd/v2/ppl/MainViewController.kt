package ltd.v2.ppl

import androidx.compose.ui.window.ComposeUIViewController
import ltd.v2.ppl.app.App
import ltd.v2.ppl.app.ContextFactory
import ltd.v2.ppl.core.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {

    App(ContextFactory(this))

}