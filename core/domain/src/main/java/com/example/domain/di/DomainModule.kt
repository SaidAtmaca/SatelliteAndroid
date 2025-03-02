package com.example.domain.di

import com.example.domain.repository.AppRepository
import com.example.domain.use_cases.SatelliteDetailUseCase
import com.example.domain.use_cases.SatelliteListUseCase
import com.example.domain.use_cases.SatellitePositionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideSatelliteListUseCase(repository: AppRepository): SatelliteListUseCase {
        return SatelliteListUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSatelliteDetailUseCase(repository: AppRepository): SatelliteDetailUseCase {
        return SatelliteDetailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSatellitePositionUseCase(repository: AppRepository): SatellitePositionUseCase {
        return SatellitePositionUseCase(repository)
    }
}