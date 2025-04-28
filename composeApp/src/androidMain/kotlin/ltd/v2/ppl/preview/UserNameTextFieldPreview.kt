package ltd.v2.ppl.preview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import ltd.v2.ppl.auth.presentation.components.UserNameTextField

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun UserNameTextFieldPreview(){
    UserNameTextField(
        username = "",
        onValueChange = {},
        hintText = "username",
        modifier = Modifier,
        focusRequester = remember { FocusRequester() },
        onNext = {

        },
        bringIntoViewRequester = remember { BringIntoViewRequester() },
        coroutineScope = rememberCoroutineScope(),

    )
}