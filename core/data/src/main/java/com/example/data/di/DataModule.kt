package com.example.data.di

import android.app.Application
import androidx.room.Room
import com.example.common.Constants
import com.example.data.local.SatelliteDatabase
import com.example.data.repository.AppRepositoryImpl
import com.example.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAppRepository(
        db: SatelliteDatabase,
    ): AppRepository {
        return AppRepositoryImpl(db.roomDao)
    }


    @Provides
    @Singleton
    fun provideSatelliteDatabase(app: Application): SatelliteDatabase {
        return Room.databaseBuilder(
            app, SatelliteDatabase::class.java, Constants.ROOM_DB_NAME
        ).build()
    }
}