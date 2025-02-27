package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.common.Constants
import com.example.data.local.entity.SatelliteDetailEntity

@Dao
interface SatelliteDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatelliteDetail(model : SatelliteDetailEntity)

    @Query("SELECT * FROM ${Constants.DETAIL_TABLE}")
    suspend fun getSatelliteDetailList() : List<SatelliteDetailEntity>

    @Query("DELETE FROM ${Constants.DETAIL_TABLE}")
    suspend fun deleteSatelliteDetailTable()
}