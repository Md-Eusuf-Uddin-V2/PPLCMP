package ltd.v2.ppl.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ltd.v2.ppl.common_utils.components.InfoDialogCompose
import ltd.v2.ppl.common_utils.components.WarningDialogCompose

@Preview(showBackground = true)
@Composable
fun WarningDialogPreview(){
    WarningDialogCompose(
        showDialog = true,
        setDismissDialog = {},
        isCloseButtonVisible = false,
        warningTitle = "Permission Denied",
        warningMsg = "Permissions were previously denied. If denied repeatedly, the system will stop showing the permission prompt. To continue using this feature, please enable the required permissions manually in the app settings.",
        buttonTitle = "Grant Permissions",
        onButtonClick = {

        }
    )
}