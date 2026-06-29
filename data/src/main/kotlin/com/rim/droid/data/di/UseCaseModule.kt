package com.rim.droid.data.di

import com.rim.droid.domain.repository.CharacterRepository
import com.rim.droid.domain.repository.EpisodeRepository
import com.rim.droid.domain.repository.LocationRepository
import com.rim.droid.domain.usecase.GetCharacters
import com.rim.droid.domain.usecase.GetEpisodes
import com.rim.droid.domain.usecase.GetLocations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetCharacters(repository: CharacterRepository): GetCharacters =
        GetCharacters(repository)

    @Provides
    @Singleton
    fun provideGetEpisodes(repository: EpisodeRepository): GetEpisodes =
        GetEpisodes(repository)

    @Provides
    @Singleton
    fun provideGetLocations(repository: LocationRepository): GetLocations =
        GetLocations(repository)
}
