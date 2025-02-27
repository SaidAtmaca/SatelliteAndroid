package com.example.data.repository

import com.example.common.Resource
import com.example.data.local.SatelliteDatabaseDao
import com.example.domain.repository.AppRepository
import com.example.model.PositionItem
import com.example.model.SatelliteDetailModel
import com.example.model.SatelliteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppRepositoryImpl(
    private val dao: SatelliteDatabaseDao
) : AppRepository {

    override fun fetchSatelliteList(): Flow<Resource<List<SatelliteModel>>> = flow {
        emit(Resource.Loading())

        /** json dan uydu listesi çekilecek. */
    }

    override fun fetchSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailModel>> = flow {
        emit(Resource.Loading())

        /** room kontrol edilecek cache'de varsa emit edilecek yoksa eğer json dan alınacak. */
    }

    override fun fetchSatellitePosition(id: Int): Flow<Resource<PositionItem>> = flow {
        emit(Resource.Loading())

        /** json dan uydu listesi çekilecek. */
    }


}