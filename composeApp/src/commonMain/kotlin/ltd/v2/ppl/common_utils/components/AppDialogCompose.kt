package ltd.v2.ppl.common_utils.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ltd.v2.ppl.auth.domain.model.DownloadModel
import ltd.v2.ppl.auth.presentation.components.DownloadItemCompose
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pplcmp.composeapp.generated.resources.Res
import pplcmp.composeapp.generated.resources.info_icon
import pplcmp.composeapp.generated.resources.info_title
import pplcmp.composeapp.generated.resources.warning_icon
import pplcmp.composeapp.generated.resources.warning_title

@Composable
fun InfoDialogCompose(
    showDialog: Boolean,
    setDismissDialog: () -> Unit,
    isCloseButtonVisible: Boolean = false,
    infoTitle: String?,
    infoMsg: String,
    onButtonClick: () -> Unit = {},
    buttonTitle: String? = null,
) {

    AnimatedVisibility(
        visible = showDialog,
        enter = fadeIn(spring(stiffness = Spring.StiffnessHigh)) + scaleIn(
            initialScale = .8f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        ),
        exit = slideOutVertically { it / 8 } + fadeOut() + scaleOut(targetScale = .95f)
    ) {
        Dialog(
            onDismissRequest = { setDismissDialog() },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            )
        ) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, end = 6.dp, start = 6.dp),
                    ) {
                        if (isCloseButtonVisible) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.End)
                                    .background(Color.Red)
                                    .clickable { }
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.White
                                )
                            }
                        }



                        Text(
                            text = infoTitle ?: stringResource(Res.string.info_title),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        )


                        Image(
                            painter = painterResource(Res.drawable.info_icon),
                            contentDescription = "Success",
                            modifier = Modifier
                                .size(54.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Text(
                            text = infoMsg,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { onButtonClick() },
                            shape = RoundedCornerShape(26.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .widthIn(min = 220.dp)
                                .height(48.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = buttonTitle ?: "Got It",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                    }
                }
            }
        }
    }
}


@Composable
fun WarningDialogCompose(
    showDialog: Boolean,
    setDismissDialog: () -> Unit,
    isCloseButtonVisible: Boolean = false,
    warningTitle: String?,
    warningMsg: String,
    onButtonClick: () -> Unit = {},
    buttonTitle: String? = null,
) {

    AnimatedVisibility(
        visible = showDialog,
        enter = fadeIn(spring(stiffness = Spring.StiffnessHigh)) + scaleIn(
            initialScale = .8f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        ),
        exit = slideOutVertically { it / 8 } + fadeOut() + scaleOut(targetScale = .95f)
    ) {
        Dialog(
            onDismissRequest = { setDismissDialog() },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            )
        ) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, end = 6.dp, start = 6.dp, bottom = 6.dp),
                    ) {
                        if (isCloseButtonVisible) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.End)
                                    .background(Color.Red)
                                    .clickable { }
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.White
                                )
                            }
                        }



                        Text(
                            text = warningTitle ?: stringResource(Res.string.warning_title),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        )


                        Image(
                            painter = painterResource(Res.drawable.warning_icon),
                            contentDescription = "Success",
                            modifier = Modifier
                                .size(54.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Text(
                            text = warningMsg,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { onButtonClick() },
                            shape = RoundedCornerShape(26.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .widthIn(min = 220.dp)
                                .height(48.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = buttonTitle ?: "Got It",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                    }
                }
            }
        }
    }
}


@Composable
fun DownloadDialogCompose(
    showDialog: Boolean,
    setDismissDialog: () -> Unit,
    downloadList: List<DownloadModel>,
    onFilesDownloadStart: (index: Int) -> Unit
) {
    if (showDialog) {
        AnimatedVisibility(
            visible = showDialog,
            enter = fadeIn(spring(stiffness = Spring.StiffnessHigh)) + scaleIn(
                initialScale = .8f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            ),
            exit = slideOutVertically { it / 8 } + fadeOut() + scaleOut(targetScale = .95f)
        ) {
            Dialog(
                onDismissRequest = { setDismissDialog() },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false,
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {

                            itemsIndexed(downloadList, key = { index, item -> item.title!! }) { index, model ->
                                if (model.mediaFiles != null) {
                                    LaunchedEffect(key1 = index) {
                                        onFilesDownloadStart(index)
                                    }
                                }
                                DownloadItemCompose(model)
                            }
                        }
                    }
                }
            }
        }
    }


}












