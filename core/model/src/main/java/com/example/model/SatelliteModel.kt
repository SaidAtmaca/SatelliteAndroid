package com.example.model

import java.io.Serializable

data class SatelliteModel(
    val id: Int ,
    val active : Boolean,
    val name : String
) : Serializable
