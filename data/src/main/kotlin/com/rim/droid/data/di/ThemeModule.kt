package com.rim.droid.data.di

import android.content.Context
import com.rim.droid.data.theme.ThemeRepositoryImpl
import com.rim.droid.domain.repository.ThemeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ThemeModule {

    @Provides
    @Singleton
    fun provideThemeRepository(
        @ApplicationContext context: Context,
    ): ThemeRepository = ThemeRepositoryImpl(context)
}
