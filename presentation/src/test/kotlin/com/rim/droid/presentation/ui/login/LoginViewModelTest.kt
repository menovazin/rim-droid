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
import org.mockito.kotlin.whenever

/**
 * spec: android-fake-login / token flow (fake login)
 */
class LoginViewModelTest : BaseTest() {

    private val sessionRepository: SessionRepository = mock()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        whenever(sessionRepository.hasToken()).thenReturn(false)
        viewModel = LoginViewModel(sessionRepository)
    }

    @Test
    fun `initial isLoggedIn mirrors repository`() = runTest {
        whenever(sessionRepository.hasToken()).thenReturn(true)
        val loggedInViewModel = LoginViewModel(sessionRepository)

        assertThat(loggedInViewModel.isLoggedIn.value).isTrue()
    }

    @Test
    fun `successful login persists token and flips state`() = runTest {
        viewModel.isSubmitting.test {
            viewModel.isLoggedIn.test {
                viewModel.onLogin("Morty")

                assertThat(awaitItem()).isFalse()
                assertThat(awaitItem()).isTrue()
            }
            assertThat(awaitItem()).isFalse()
            assertThat(awaitItem()).isTrue()
        }
        verify(sessionRepository).saveToken(any())
    }

    @Test
    fun `onLogin is idempotent while submitting`() = runTest {
        viewModel.onLogin("Morty")
        viewModel.onLogin("Morty")

        verify(sessionRepository).saveToken(any())
        assertThat(viewModel.isLoggedIn.value).isTrue()
    }
}
