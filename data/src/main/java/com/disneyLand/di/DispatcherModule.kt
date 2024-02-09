package com.disneyLand.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object DispatcherModule {

    @MainDispatcher
    @Provides
    fun provideMainDispatcher() = Dispatchers.Main

    @IODispatcher
    @Provides
    fun provideIODispatcher() = Dispatchers.IO
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher
