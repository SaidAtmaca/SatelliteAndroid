package com.example.model

import java.io.Serializable

data class PositionItem(
    val id: String,
    val positions: List<PositionModel>
):Serializable
