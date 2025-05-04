package ltd.v2.ppl.core.data_source

import ltd.v2.ppl.globalAppContext
import okio.Sink
import okio.buffer
import okio.sink
import java.io.File


actual fun createFileOutputStream(fileName: String): Sink {

    val folder = File(globalAppContext.cacheDir, "video_image")

    if (!folder.exists()) {
        folder.mkdirs()
    }

    val file = File(folder, fileName)
    return file.sink().buffer()
}