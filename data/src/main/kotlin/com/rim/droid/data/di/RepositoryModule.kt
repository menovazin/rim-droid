package com.rim.droid.data.di

import com.rim.droid.data.repository.CharacterRepositoryImpl
import com.rim.droid.data.repository.EpisodeRepositoryImpl
import com.rim.droid.data.repository.LocationRepositoryImpl
import com.rim.droid.domain.repository.CharacterRepository
import com.rim.droid.domain.repository.EpisodeRepository
import com.rim.droid.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    @Singleton
    abstract fun bindEpisodeRepository(impl: EpisodeRepositoryImpl): EpisodeRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository
}
