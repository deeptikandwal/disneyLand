package com.disneyLand.di

import com.disneyLand.usecase.DisneyActorUsecase
import com.disneyLand.usecase.DisneyActorUsecaseImpl
import com.disneyLand.usecase.DisneyCharactersListUsecase
import com.disneyLand.usecase.DisneyCharactersListUsecaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UsecaseModule {

    @Binds
    abstract fun bindDisneyCharactersListUsecase(disneyCharactersListUsecaseImpl: DisneyCharactersListUsecaseImpl): DisneyCharactersListUsecase

    @Binds
    abstract fun bindDisneyActorUsecase(
        disneyActorUsecaseImpl: DisneyActorUsecaseImpl
    ): DisneyActorUsecase
}
