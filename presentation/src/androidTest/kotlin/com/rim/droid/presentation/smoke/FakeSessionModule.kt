package com.rim.droid.presentation.smoke

import com.rim.droid.data.di.SessionModule
import com.rim.droid.domain.repository.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SessionModule::class],
)
object FakeSessionModule {

    val session: FakeSessionRepository = FakeSessionRepository()

    @Provides
    @Singleton
    fun provideSessionRepository(): SessionRepository = session
}
