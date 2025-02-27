package com.example.domain.repository


import com.example.common.Resource
import com.example.model.PositionItem
import com.example.model.SatelliteDetailModel
import com.example.model.SatelliteModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {


    fun fetchSatelliteList() : Flow<Resource<List<SatelliteModel>>>

    fun fetchSatelliteDetail(id:Int) : Flow<Resource<SatelliteDetailModel>>

    fun fetchSatellitePosition(id: Int) : Flow<Resource<PositionItem>>
}