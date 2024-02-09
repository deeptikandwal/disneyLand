package com.disneyLand.di

import com.disneyLand.repository.DisneyCharactersRepository
import com.disneyLand.repository.DisneyCharactersRepositoryImpl
import com.disneyLand.source.ActorMapper
import com.disneyLand.source.ActorMapperImpl
import com.disneyLand.source.DisneyMapper
import com.disneyLand.source.DisneyMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindDisneyListRepository(disneyListRepositoryImpl: DisneyCharactersRepositoryImpl): DisneyCharactersRepository

    @Binds
    abstract fun bindActorMapper(actorMapperImpl: ActorMapperImpl): ActorMapper

    @Binds
    abstract fun bindDisneyMapper(disneyMapperImpl: DisneyMapperImpl): DisneyMapper
}
