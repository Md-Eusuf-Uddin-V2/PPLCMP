package ltd.v2.ppl.common_utils.utils

import okio.FileSystem
import okio.Path
import okio.SYSTEM
import okio.Sink
import okio.buffer

object FileHelper {

     fun getVideoImageFolder(packageName: String): Path {
        val cacheDir: Path = getCacheDirectory()
        val videoDir = cacheDir / "video_image"

        val fs = FileSystem.SYSTEM
        if (!fs.exists(videoDir)) {
            fs.createDirectories(videoDir)
        }

        return videoDir
    }

    fun getFilePath(packageName: String, fileName: String): Path {
        return getVideoImageFolder(packageName) / fileName
    }

    fun createFileSink(packageName: String, fileName: String): Sink {
        val filePath = getFilePath(packageName, fileName)
        return FileSystem.SYSTEM.sink(filePath).buffer()
    }

    fun fileExists(packageName: String, fileName: String): Boolean {
        val filePath = getFilePath(packageName, fileName)
        return FileSystem.SYSTEM.exists(filePath)
    }
}

