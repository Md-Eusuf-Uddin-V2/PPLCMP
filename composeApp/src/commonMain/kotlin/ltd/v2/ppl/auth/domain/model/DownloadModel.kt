package ltd.v2.ppl.auth.domain.model

data class DownloadModel(
    val type: String? = null,
    val title: String? = null,
    val mediaFiles: List<MediaFile>? = null,
    val progress: Double = 0.0,
    val downloadProgress: DownloadProgress? = null
)

data class MediaFile(
    val url: String,
    val md5: String? = null
)

data class DownloadProgress(
    val currentFileIndex: Int,
    val progress: Double,
    val totalFiles: Int
)