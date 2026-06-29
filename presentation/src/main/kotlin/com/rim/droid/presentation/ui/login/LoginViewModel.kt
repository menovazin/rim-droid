package com.rim.droid.presentation.ui.login

import androidx.lifecycle.ViewModel
import com.rim.droid.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(sessionRepository.hasToken())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    fun onLogin(name: String) {
        val token = UUID.randomUUID().toString()
        sessionRepository.saveToken(token)
        _isLoggedIn.value = true
    }
}
