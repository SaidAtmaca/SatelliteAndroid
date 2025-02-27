package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.entity.SatelliteDetailEntity

@Database(entities = [SatelliteDetailEntity::class], version = 1, exportSchema = false)
abstract class SatelliteDatabase : RoomDatabase() {

    abstract val roomDao : SatelliteDatabaseDao

}