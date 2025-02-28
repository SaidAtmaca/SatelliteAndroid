package com.example.domain.repository


import android.content.Context
import com.example.common.Resource
import com.example.model.PositionItem
import com.example.model.PositionModel
import com.example.model.SatelliteDetailModel
import com.example.model.SatelliteModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {


    fun fetchSatelliteList(context: Context) : Flow<Resource<List<SatelliteModel>>>

    fun fetchSatelliteDetail(context: Context,id:Int) : Flow<Resource<SatelliteDetailModel>>

    fun fetchSatellitePosition(context: Context,id: Int) : Flow<Resource<List<PositionModel>>>
}