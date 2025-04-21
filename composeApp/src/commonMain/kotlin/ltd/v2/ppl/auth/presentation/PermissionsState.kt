package ltd.v2.ppl.auth.presentation

import dev.icerock.moko.permissions.Permission

data class PermissionsState(
    val deniedPermissions: List<Permission> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)