package ltd.v2.ppl.auth.presentation

data class LoginState(
    val showPermissionInfoPopup: Boolean = false,
    val permissionDeniedPopup: Boolean = false,
    val error: String? = null,
    val username: String = "",
    val password: String = "",
    val noInternetAvailable: Boolean = false,
    val usernameError: Boolean = false,
    val passwordError: Boolean = false,
    val isLoginBtnLoading: Boolean = false,
    val noAccess: Boolean = false

)