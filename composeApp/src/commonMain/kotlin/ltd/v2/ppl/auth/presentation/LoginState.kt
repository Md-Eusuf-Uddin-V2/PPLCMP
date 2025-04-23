package ltd.v2.ppl.auth.presentation

data class LoginState(
    val showPermissionInfoPopup: Boolean = false,
    val permissionDeniedPopup: Boolean = false,
    val error: String? = null
)