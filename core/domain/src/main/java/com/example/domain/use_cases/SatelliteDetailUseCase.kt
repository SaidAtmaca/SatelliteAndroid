package com.example.domain.use_cases

import android.content.Context
import com.example.common.Resource
import com.example.domain.repository.AppRepository
import com.example.model.SatelliteDetailModel
import com.example.model.SatelliteModel
import kotlinx.coroutines.flow.Flow

class SatelliteDetailUseCase (
    private val repository: AppRepository
) {

    fun fetchSatelliteDetail(context: Context,id:Int) : Flow<Resource<SatelliteDetailModel>> {
        return repository.fetchSatelliteDetail(context,id)
    }
}