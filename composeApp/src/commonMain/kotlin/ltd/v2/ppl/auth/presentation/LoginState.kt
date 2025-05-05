package ltd.v2.ppl.auth.presentation

import ltd.v2.ppl.auth.domain.model.DownloadModel

data class LoginState(
    val showPermissionInfoPopup: Boolean = false,
    val permissionDeniedPopup: Boolean = false,
    val error: String? = null,
    val username: String = "",
    val password: String = "",
    val noInternetAvailable: Boolean = false,
    val usernameError: Boolean = false,
    val passwordError: Boolean = false,
    val isDownloadDialogShow: Boolean = false,
    val noAccess: Boolean = false,
    val downloadList: List<DownloadModel> = emptyList(),
    val shouldNavigateToDashBoard: Boolean = false,
    val shouldNavigateToAttendance: Boolean = false,

)