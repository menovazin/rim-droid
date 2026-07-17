package com.rim.droid.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rim.droid.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

/**
 * One-shot UI events for [LoginScreen].
 */
sealed interface LoginUiEvent {
    data object NavigateHome : LoginUiEvent
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting.asStateFlow()

    private val _uiEvents = Channel<LoginUiEvent>(Channel.BUFFERED)
    val uiEvents = _uiEvents.receiveAsFlow()

    /**
     * Fake-login: persists a random UUID token and emits [LoginUiEvent.NavigateHome].
     *
     * [name] is collected by the UI for the login form (openspec login-screen-ui) but is not
     * persisted; the session token is always a generated UUID. Product decision: name is UI-only.
     */
    fun onLogin(name: String) {
        if (_isSubmitting.value) return
        _isSubmitting.value = true
        viewModelScope.launch {
            val token = UUID.randomUUID().toString()
            sessionRepository.saveToken(token)
            _uiEvents.send(LoginUiEvent.NavigateHome)
        }
    }
}
