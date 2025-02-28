package com.example.domain.use_cases

import android.content.Context
import com.example.common.Resource
import com.example.domain.repository.AppRepository
import com.example.model.PositionItem
import com.example.model.SatelliteModel
import kotlinx.coroutines.flow.Flow

class SatelliteListUseCase(
    private val repository: AppRepository
) {

    fun fetchSatelliteList(context: Context) : Flow<Resource<List<SatelliteModel>>> {
        return repository.fetchSatelliteList(context)
    }
}