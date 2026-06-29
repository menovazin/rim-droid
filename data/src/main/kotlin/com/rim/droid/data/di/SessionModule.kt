package com.rim.droid.data.di

import android.content.Context
import com.rim.droid.data.session.SessionRepositoryImpl
import com.rim.droid.domain.repository.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Provides
    @Singleton
    fun provideSessionRepository(
        @ApplicationContext context: Context,
    ): SessionRepository = SessionRepositoryImpl(context)
}
