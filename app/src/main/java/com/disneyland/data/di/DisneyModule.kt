package com.disneyland.data.di

import com.disneyland.data.repository.DisneyCharactersRepositoryImpl
import com.disneyland.domain.repository.DisneyCharactersRepository
import com.disneyland.domain.usecase.DisneyActorUsecase
import com.disneyland.domain.usecase.DisneyActorUsecaseImpl
import com.disneyland.domain.usecase.DisneyCharactersListUsecase
import com.disneyland.domain.usecase.DisneyCharactersListUsecaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DisneyModule {

    @Binds
    abstract fun bindDisneyListRepository(disneyListRepositoryImpl: DisneyCharactersRepositoryImpl): DisneyCharactersRepository

    @Binds
    abstract fun bindDisneyCharactersListUsecase(disneyCharactersListUsecaseImpl: DisneyCharactersListUsecaseImpl): DisneyCharactersListUsecase

    @Binds
    abstract fun bindDisneyActorUsecase(
        disneyActorUsecaseImpl: DisneyActorUsecaseImpl,
    ): DisneyActorUsecase

}