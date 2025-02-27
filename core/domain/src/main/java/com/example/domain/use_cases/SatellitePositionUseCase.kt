package com.example.domain.use_cases

import com.example.common.Resource
import com.example.domain.repository.AppRepository
import com.example.model.PositionItem
import kotlinx.coroutines.flow.Flow

class SatellitePositionUseCase(
    private val repository: AppRepository
) {

    fun fetchSatellitePositions(id:Int) : Flow<Resource<PositionItem>> {
        return repository.fetchSatellitePosition(id)
    }
}