package ltd.v2.ppl.common_utils.utils

import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual fun getCacheDirectory(): Path {
    val paths = NSSearchPathForDirectoriesInDomains(
        NSCachesDirectory,
        NSUserDomainMask,
        true
    )
    val cachePath = paths.firstOrNull() as? String ?: error("Cache directory not found")
    return cachePath.toPath()
}