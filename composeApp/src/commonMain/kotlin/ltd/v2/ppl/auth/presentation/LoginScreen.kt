package ltd.v2.ppl.auth.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import ltd.v2.ppl.auth.presentation.components.LoginButton
import ltd.v2.ppl.auth.presentation.components.PasswordTextField
import ltd.v2.ppl.auth.presentation.components.UserNameTextField
import ltd.v2.ppl.common_utils.components.DownloadDialogCompose
import ltd.v2.ppl.common_utils.components.InfoDialogCompose
import ltd.v2.ppl.common_utils.components.WarningDialogCompose
import ltd.v2.ppl.common_utils.utils.getAppVersionCode
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pplcmp.composeapp.generated.resources.Res
import pplcmp.composeapp.generated.resources.app_logo
import pplcmp.composeapp.generated.resources.app_name
import pplcmp.composeapp.generated.resources.grant_permissions
import pplcmp.composeapp.generated.resources.info_msg
import pplcmp.composeapp.generated.resources.login_button
import pplcmp.composeapp.generated.resources.password
import pplcmp.composeapp.generated.resources.permission_denied
import pplcmp.composeapp.generated.resources.permission_required
import pplcmp.composeapp.generated.resources.username
import pplcmp.composeapp.generated.resources.vtwo_ltd
import pplcmp.composeapp.generated.resources.warning_msg


@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel,
    controller: PermissionsController,
) {

    BindEffect(controller)

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit){
        viewModel.oneTimeState.collect{ onTimeState ->
            if (onTimeState.noInternetAvailable){
                showToast(
                    "No Internet Available",

                    )

            }
            if (onTimeState.noAccess){
                showToast(
                    "No Access found")
            }
        }
    }

    Box(Modifier.fillMaxSize()) {

        when {
            state.showPermissionInfoPopup -> {
                InfoDialogCompose(
                    showDialog = state.showPermissionInfoPopup,
                    setDismissDialog = {

                    },
                    isCloseButtonVisible = false,
                    infoTitle = stringResource(Res.string.permission_required),
                    infoMsg = stringResource(Res.string.info_msg),
                    buttonTitle = stringResource(Res.string.grant_permissions),
                    onButtonClick = {
                        viewModel.processIntent(LoginEvents.RequestPermissions)
                    },
                )
            }

            state.permissionDeniedPopup -> {
                WarningDialogCompose(
                    showDialog = state.permissionDeniedPopup,
                    setDismissDialog = {

                    },
                    isCloseButtonVisible = false,
                    warningTitle = stringResource(Res.string.permission_denied),
                    warningMsg = stringResource(Res.string.warning_msg),
                    buttonTitle = stringResource(Res.string.grant_permissions),
                    onButtonClick = {
                        viewModel.processIntent(LoginEvents.RequestPermissionsFromDenied)
                    },
                )
            }

            state.isDownloadDialogShow -> {
                DownloadDialogCompose(
                    showDialog = state.isDownloadDialogShow,
                    setDismissDialog = {

                    },
                    downloadList = state.downloadList,
                    onFilesDownloadStart = { mIndex ->
                        viewModel.processIntent(LoginEvents.OnFilesDownloadStart(index = mIndex))
                    }
                )
            }


        }

        LoginScreen(
            state = state,
            onUsernameChanged = viewModel::onUsernameChanged,
            onPasswordChanged = viewModel::onPasswordChanged,
            onLogin = {
                viewModel.processIntent(LoginEvents.OnLoginClicked)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    state: LoginState,
    modifier: Modifier = Modifier,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLogin: () -> Unit
) {
    val scrollState = rememberScrollState()
    val usernameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = modifier.height(40.dp))

        Image(
            painter = painterResource(Res.drawable.app_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = modifier.height(32.dp))

        Text(
            text = stringResource(Res.string.login_button),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = modifier.height(32.dp))

        UserNameTextField(
            username = state.username,
            onValueChange = onUsernameChanged,
            hintText = stringResource(Res.string.username),
            modifier = modifier,
            focusRequester = usernameFocusRequester,
            onNext = {
                passwordFocusRequester.requestFocus()
            },
            isError = state.usernameError,
            errorMessage = if (state.usernameError) "UserName cannot be empty" else "",
        )

        Spacer(modifier = modifier.height(16.dp))

        PasswordTextField(
            userPassword = state.password,
            onValueChange = onPasswordChanged,
            hintText = stringResource(Res.string.password),
            modifier = modifier,
            focusRequester = passwordFocusRequester,
            onDone = {
                keyboardController?.hide()
            },
            bringIntoViewRequester = bringIntoViewRequester,
            coroutineScope = coroutineScope,
            isError = state.passwordError,
            errorMessage = if (state.passwordError) "Password cannot be empty" else "",
        )

        Spacer(modifier = modifier.height(32.dp))

        LoginButton(
            onClick = {
                keyboardController?.hide()
                onLogin()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = true,
            isLoading = false
        )

        Spacer(modifier = modifier.height(42.dp))

        Text(
            "${stringResource(Res.string.app_name)} v ${getAppVersionCode()}",
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.labelSmall
        )

        Text(
            stringResource(Res.string.vtwo_ltd),
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleSmall
        )
    }
}




