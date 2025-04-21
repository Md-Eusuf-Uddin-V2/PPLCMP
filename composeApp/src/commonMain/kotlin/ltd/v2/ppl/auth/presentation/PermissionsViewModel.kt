package ltd.v2.ppl.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PermissionsViewModel(
    private val controller: PermissionsController
) : ViewModel() {

    private val _state = MutableStateFlow(PermissionsState())
    val state: StateFlow<PermissionsState> = _state.asStateFlow()

    private val requiredPermissions = listOf(
        Permission.RECORD_AUDIO,
        Permission.STORAGE,
        Permission.LOCATION,
        Permission.CAMERA
    )

    fun processIntent(intent: PermissionsEvent) {
        when (intent) {
            PermissionsEvent.RequestPermissions -> checkPermissions()
        }
    }

    private fun checkPermissions() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val denied = mutableListOf<Permission>()

            requiredPermissions.forEach { permission ->
                try {
                    controller.providePermission(permission)
                } catch (e: DeniedAlwaysException) {
                    denied.add(permission)
                } catch (e: DeniedException) {
                    denied.add(permission)
                } catch (e: Exception) {
                    _state.update { it.copy(error = "Unexpected error") }
                }
            }

            _state.update {
                it.copy(
                    deniedPermissions = denied,
                    isLoading = false,
                    error =  null
                )
            }
        }
    }
}




