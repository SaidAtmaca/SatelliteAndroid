package com.example.domain.use_cases

import com.example.common.Resource
import com.example.domain.repository.AppRepository
import com.example.model.PositionItem
import com.example.model.SatelliteModel
import kotlinx.coroutines.flow.Flow

class SatelliteListUseCase(
    private val repository: AppRepository
) {

    fun fetchSatelliteList() : Flow<Resource<List<SatelliteModel>>> {
        return repository.fetchSatelliteList()
    }
}