package ltd.v2.ppl.auth.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect


@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel,
    controller: PermissionsController,
) {

    BindEffect(controller)

    val state by viewModel.state.collectAsState()

    Box(Modifier.fillMaxSize()) {

        when {
            state.showPermissionInfoPopup -> {
                AlertDialog(
                    onDismissRequest = {},
                    title = { Text("Permissions Required") },
                    text = { Text("This app needs permissions to function correctly. Please grant them.") },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.processIntent(LoginEvents.RequestPermissions)
                        }) {
                            Text("OK")
                        }
                    }
                )
            }

            state.permissionDeniedPopup -> {
                AlertDialog(
                    onDismissRequest = {},
                    title = { Text("Permissions Denied") },
                    text = { Text("This app needs permissions to function correctly. Please grant them.") },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.processIntent(LoginEvents.RequestPermissionsFromDenied)
                        }) {
                            Text("OK")
                        }
                    }
                )
            }
        }

        LoginScreen(
            state = state,
            onLogin = { username, password ->
                viewModel.processIntent(LoginEvents.OnLoginClicked)
            }
        )
    }
}

@Composable
fun LoginScreen(
    state: LoginState,
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onLogin(username, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            state.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Error: $it", color = Color.Red)
            }
        }
    }
}


