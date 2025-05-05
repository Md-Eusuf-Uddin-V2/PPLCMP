package ltd.v2.ppl.common_utils.utils

import ltd.v2.ppl.globalAppContext

actual fun getAppPackageName(): String {
    return globalAppContext.packageName
}