package ltd.v2.ppl.auth.presentation


sealed interface LoginEvents {
      data object RequestPermissions : LoginEvents
      data object RequestPermissionsFromDenied : LoginEvents
      data object OnLoginClicked : LoginEvents
}