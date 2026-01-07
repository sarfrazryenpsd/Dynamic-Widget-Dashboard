package com.ryen.dynamicwidgetdashboard.di

import com.ryen.dynamicwidgetdashboard.data.banner.BannerRepositoryImpl
import com.ryen.dynamicwidgetdashboard.data.list.ListRepositoryImpl
import com.ryen.dynamicwidgetdashboard.data.metadata.WidgetMetadataRepositoryImpl
import com.ryen.dynamicwidgetdashboard.domain.banner.BannerRepository
import com.ryen.dynamicwidgetdashboard.domain.list.ListRepository
import com.ryen.dynamicwidgetdashboard.domain.metadata.WidgetMetadataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBannerRepository(
        impl: BannerRepositoryImpl
    ): BannerRepository

    @Binds
    @Singleton
    abstract fun bindListRepository(
        impl: ListRepositoryImpl
    ): ListRepository

    @Binds
    @Singleton
    abstract fun bindWidgetMetadataRepository(
        impl: WidgetMetadataRepositoryImpl
    ): WidgetMetadataRepository


}