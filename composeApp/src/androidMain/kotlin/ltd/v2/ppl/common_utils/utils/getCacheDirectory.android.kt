package ltd.v2.ppl.common_utils.utils

import ltd.v2.ppl.globalAppContext
import okio.Path
import okio.Path.Companion.toPath

actual fun getCacheDirectory(): Path {
    val dir = globalAppContext.cacheDir
    return dir.absolutePath.toPath()
}