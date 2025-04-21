package ltd.v2.ppl.auth.presentation

sealed class PermissionsEvent {
    data object RequestPermissions : PermissionsEvent()
}