package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.common.Constants
import java.io.Serializable

@Entity(tableName = Constants.DETAIL_TABLE)
data class SatelliteDetailEntity(
    @PrimaryKey
    val id: Int,
    val costPerLaunch: Long,
    val firstFlight: String,
    val height: Int,
    val mass: Int
) : Serializable
