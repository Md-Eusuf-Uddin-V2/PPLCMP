package ltd.v2.ppl.auth.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ltd.v2.ppl.auth.domain.model.DownloadModel
import org.jetbrains.compose.resources.painterResource
import pplcmp.composeapp.generated.resources.Res
import pplcmp.composeapp.generated.resources.ic_complete

@Composable
fun DownloadItemCompose(model: DownloadModel) {
    val progress = model.downloadProgress?.progress?.coerceIn(0.0, 1.0) ?: 0.0
    val isCompleted = model.downloadProgress?.currentFileIndex != 0 &&
            model.downloadProgress?.currentFileIndex == model.downloadProgress?.totalFiles &&
            progress == 1.0

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isCompleted) {
                Icon(
                    painter = painterResource(resource = Res.drawable.ic_complete),
                    contentDescription = "Completed",
                    modifier = Modifier.size(36.dp),
                    tint = Color.Unspecified
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(36.dp)
                ) {
                    CircularProgressIndicator(
                        progress = { progress.toFloat() },
                        modifier = Modifier.fillMaxSize(),
                        strokeWidth = 3.dp,
                        strokeCap = StrokeCap.Round,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = model.title ?: "",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${model.downloadProgress?.currentFileIndex ?: 0} of ${model.downloadProgress?.totalFiles ?: 0}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.DarkGray
                    )
                )
            }
        }
    }
}