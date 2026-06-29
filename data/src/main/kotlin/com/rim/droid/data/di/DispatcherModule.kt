package com.rim.droid.data.di

import com.rim.droid.data.util.DispatchersProviderImpl
import com.rim.droid.domain.util.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatchersProvider(): DispatchersProvider = DispatchersProviderImpl()
}
