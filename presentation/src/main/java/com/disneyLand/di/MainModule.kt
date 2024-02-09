package com.disneyLand.di

import com.disneyLand.source.DetailScreenMapper
import com.disneyLand.source.DetailScreenMapperImpl
import com.disneyLand.source.HomeScreenMapper
import com.disneyLand.source.HomeScreenMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainModule {

    @Binds
    abstract fun bindDetailScreenMapper(detailScreenMapperImpl: DetailScreenMapperImpl): DetailScreenMapper

    @Binds
    abstract fun bindHomeScreenMapper(homeScreenMapperImpl: HomeScreenMapperImpl): HomeScreenMapper
}
