package com.rim.droid.presentation.smoke

import com.rim.droid.data.di.RepositoryModule
import com.rim.droid.domain.repository.AvatarUrlProvider
import com.rim.droid.domain.repository.CharacterRepository
import com.rim.droid.domain.repository.EpisodeRepository
import com.rim.droid.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class],
)
object FakeRepositoryModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(): CharacterRepository = FailingCharacterRepository()

    @Provides
    @Singleton
    fun provideEpisodeRepository(): EpisodeRepository = FailingEpisodeRepository()

    @Provides
    @Singleton
    fun provideLocationRepository(): LocationRepository = FailingLocationRepository()

    @Provides
    @Singleton
    fun provideAvatarUrlProvider(): AvatarUrlProvider = FakeAvatarUrlProvider()
}
