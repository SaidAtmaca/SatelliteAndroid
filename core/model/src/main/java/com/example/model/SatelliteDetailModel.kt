package com.example.model

import java.io.Serializable

data class SatelliteDetailModel(
    val id: Int,
    val cost_per_launch: Int,
    val first_flight: String,
    val height: Int,
    val mass: Int
):Serializable
