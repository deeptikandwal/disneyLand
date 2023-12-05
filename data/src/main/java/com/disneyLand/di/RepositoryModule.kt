package com.disneyLand.di

import com.disneyLand.repository.DisneyCharactersRepository
import com.disneyLand.repository.DisneyCharactersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindDisneyListRepository(disneyListRepositoryImpl: DisneyCharactersRepositoryImpl): DisneyCharactersRepository
}
