package ltd.v2.ppl.core.data_source

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.Sink
import okio.buffer
import platform.Foundation.NSTemporaryDirectory

actual fun createFileOutputStream(fileName: String): Sink {
    val tempDir = NSTemporaryDirectory()
    val fullPath: Path = (tempDir + fileName).toPath()
    return FileSystem.SYSTEM.sink(fullPath).buffer()
}