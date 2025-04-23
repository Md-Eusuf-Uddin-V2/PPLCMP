package ltd.v2.ppl.auth.presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ltd.v2.ppl.common_utils.Constants

class LoginViewModel(
    private val controller: PermissionsController,
    private val appPref: DataStore<Preferences>
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()


    private val requiredPermissions = listOf(
        Permission.RECORD_AUDIO,
        Permission.STORAGE,
        Permission.LOCATION,
        Permission.CAMERA
    )

    init {
        checkFirstLogin()
    }

    fun processIntent(intent: LoginEvents) {
        when (intent) {
            LoginEvents.RequestPermissions -> handleFirstPermissionRequest()
            LoginEvents.RequestPermissionsFromDenied -> requestPermissionsFromDenied()
            LoginEvents.OnLoginClicked -> {
                checkPermissionsBeforeLogin()
            }
        }
    }

    private fun checkFirstLogin() {
        viewModelScope.launch {
            val isFirstLogin = appPref.data
                .map { prefs ->
                    prefs[booleanPreferencesKey(Constants.isFirstLogin)] ?: true
                }.first()

            if (isFirstLogin) {
                _state.update { it.copy(showPermissionInfoPopup = true) }
            } else {
                requestPermissions()
            }
        }
    }

    private fun handleFirstPermissionRequest() {
        viewModelScope.launch {
            appPref.edit { it[booleanPreferencesKey(Constants.isFirstLogin)] = false }

            _state.update {
                it.copy(showPermissionInfoPopup = false)
            }

            requestPermissions()
        }
    }

    private fun requestPermissionsFromDenied() {
        viewModelScope.launch {
            _state.update { it.copy(permissionDeniedPopup = false) }
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        viewModelScope.launch {
            requiredPermissions.forEach { permission ->
                try {
                    if (!controller.isPermissionGranted(permission)) {
                        controller.providePermission(permission)
                    }
                } catch (_: Exception) {

                }
            }
        }
    }

    private fun checkPermissionsBeforeLogin() {
        viewModelScope.launch {
            val allGranted = requiredPermissions.all { permission ->
                try {
                    controller.isPermissionGranted(permission)
                } catch (e: Exception) {
                    false
                }
            }

            _state.update { it.copy(permissionDeniedPopup = true) }

            if (allGranted) {

            }
        }
    }
}







