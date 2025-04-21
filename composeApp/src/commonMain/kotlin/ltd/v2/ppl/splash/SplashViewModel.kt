package ltd.v2.ppl.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {


    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        startCountdown()
    }

    private fun startCountdown() {
        viewModelScope.launch {
            delay(3000L)
            _state.update { it.copy(shouldNavigate = true) }
        }
    }
}

