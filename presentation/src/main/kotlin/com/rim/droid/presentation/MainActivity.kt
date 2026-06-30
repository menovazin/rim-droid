package com.rim.droid.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.rim.droid.R
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rim.droid.domain.repository.SessionRepository
import com.rim.droid.presentation.navigation.RimNavHost
import com.rim.droid.presentation.theme.RimTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionRepository: SessionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RIM)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RimTheme {
                RimNavHost(
                    hasToken = sessionRepository.hasToken(),
                    onLogout = { sessionRepository.clear() },
                )
            }
        }
    }
}
