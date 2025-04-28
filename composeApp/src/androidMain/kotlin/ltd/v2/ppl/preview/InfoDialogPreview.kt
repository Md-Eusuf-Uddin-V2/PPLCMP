package ltd.v2.ppl.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ltd.v2.ppl.common_utils.components.InfoDialogCompose

@Preview(showBackground = true)
@Composable
fun InfoDialogPreview(){
    InfoDialogCompose(
        showDialog = true,
        setDismissDialog = {},
        isCloseButtonVisible = false,
        infoTitle = "Permission Required",
        infoMsg = "This app requires camera, microphone, location and storage permission to function properly. Please grant these permissions.",
        buttonTitle = "Grant Permissions",
        onButtonClick = {

        }
    )
}