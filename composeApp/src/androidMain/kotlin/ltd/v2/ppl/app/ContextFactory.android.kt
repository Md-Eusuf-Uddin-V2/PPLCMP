package ltd.v2.ppl.app

import androidx.activity.ComponentActivity

actual class ContextFactory(private val activity: ComponentActivity) {
    actual fun getContext(): Any = activity.baseContext

    actual fun getApplication(): Any  = activity.application

    actual fun getActivity(): Any  = activity
}