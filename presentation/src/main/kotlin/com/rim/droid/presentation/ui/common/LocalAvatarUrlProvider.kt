package com.rim.droid.presentation.ui.common

import androidx.compose.runtime.staticCompositionLocalOf
import com.rim.droid.domain.repository.AvatarUrlProvider

/**
 * App-scoped [AvatarUrlProvider] provided at the composition root.
 */
val LocalAvatarUrlProvider = staticCompositionLocalOf<AvatarUrlProvider> {
    error("LocalAvatarUrlProvider not provided")
}
