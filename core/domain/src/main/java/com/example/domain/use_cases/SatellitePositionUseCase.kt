package com.example.domain.use_cases

import android.content.Context
import com.example.common.Resource
import com.example.domain.repository.AppRepository
import com.example.model.PositionItem
import com.example.model.PositionModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SatellitePositionUseCase @Inject constructor(
    private val repository: AppRepository
) {
    fun fetchSatellitePositions(context: Context,id:Int) : Flow<Resource<List<PositionModel>>> {
        return repository.fetchSatellitePosition(context,id)
    }
}