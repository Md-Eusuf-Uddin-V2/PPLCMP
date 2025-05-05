package ltd.v2.ppl.common_utils.utils

import platform.Foundation.NSBundle

actual fun getAppPackageName(): String {
    return NSBundle.mainBundle.bundleIdentifier ?: "unknown"
}