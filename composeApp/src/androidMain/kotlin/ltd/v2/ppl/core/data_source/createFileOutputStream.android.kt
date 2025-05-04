package ltd.v2.ppl.core.data_source

import ltd.v2.ppl.app.ContextFactory
import ltd.v2.ppl.globalAppContext
import okio.Sink
import okio.buffer
import okio.sink
import java.io.File



actual fun createFileOutputStream(fileName: String): Sink {

    val file = File(globalAppContext.cacheDir, fileName)
    return file.sink().buffer()
}