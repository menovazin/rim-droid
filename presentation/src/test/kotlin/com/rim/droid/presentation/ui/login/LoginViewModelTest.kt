package com.rim.droid.presentation.ui.login

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rim.droid.core.test.base.BaseTest
import com.rim.droid.domain.repository.SessionRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

/**
 * spec: android-fake-login / token flow (fake login)
 */
class LoginViewModelTest : BaseTest() {

    private val sessionRepository: SessionRepository = mock()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(sessionRepository)
    }

    @Test
    fun `successful login persists token and emits NavigateHome`() = runTest {
        viewModel.uiEvents.test {
            viewModel.isSubmitting.test {
                viewModel.onLogin("Morty")

                assertThat(awaitItem()).isFalse()
                assertThat(awaitItem()).isTrue()
            }
            assertThat(awaitItem()).isEqualTo(LoginUiEvent.NavigateHome)
        }
        verify(sessionRepository).saveToken(any())
    }

    @Test
    fun `onLogin is idempotent while submitting`() = runTest {
        viewModel.uiEvents.test {
            viewModel.onLogin("Morty")
            viewModel.onLogin("Morty")

            assertThat(awaitItem()).isEqualTo(LoginUiEvent.NavigateHome)
            expectNoEvents()
        }
        verify(sessionRepository).saveToken(any())
    }
}
