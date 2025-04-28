package ltd.v2.ppl.common_utils.utils

import androidx.compose.runtime.Composable
import platform.Foundation.NSBundle

@Composable
actual fun getAppVersionCode(): String {
    val bundle = NSBundle.mainBundle
    return bundle.objectForInfoDictionaryKey("CFBundleVersion")?.toString() ?: "Unknown"
}