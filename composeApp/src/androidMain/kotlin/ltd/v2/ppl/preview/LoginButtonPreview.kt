package ltd.v2.ppl.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ltd.v2.ppl.auth.presentation.components.LoginButton

@Preview(showBackground = true)
@Composable
fun LoginButtonPreview(){
    LoginButton(
        onClick = {},
        modifier = Modifier,
        enabled = true,
        btnText = "Login"
    )
}