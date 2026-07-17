package com.rim.droid.data.util

import com.rim.droid.domain.repository.AvatarUrlProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AvatarUrlProviderImpl @Inject constructor() : AvatarUrlProvider {
    override fun fromCharacterId(id: Int): String = AvatarUrlUtils.avatarUrlFromId(id)
}
