package ltd.v2.ppl.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ltd.v2.ppl.auth.presentation.LoginScreen
import ltd.v2.ppl.auth.presentation.LoginState

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        state = LoginState(),
        onUsernameChanged = {},
        onPasswordChanged = {},
        onLogin = {

        },

    )
}