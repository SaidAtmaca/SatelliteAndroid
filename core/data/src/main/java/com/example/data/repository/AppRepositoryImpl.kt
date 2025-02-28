package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.common.Resource
import com.example.common.readAssetFile
import com.example.data.local.SatelliteDatabaseDao
import com.example.data.local.entity.mapper.SatelliteDetailEntityMapper
import com.example.domain.repository.AppRepository
import com.example.model.PositionItem
import com.example.model.PositionListModel
import com.example.model.PositionModel
import com.example.model.SatelliteDetailModel
import com.example.model.SatelliteModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class AppRepositoryImpl(
    private val dao: SatelliteDatabaseDao
) : AppRepository {

    override fun fetchSatelliteList(context: Context): Flow<Resource<List<SatelliteModel>>> = flow {
        emit(Resource.Loading())
        try {

            val jsonString = readAssetFile(context,"SATELLITE-LIST.json")
            val satelliteList: List<SatelliteModel> = Gson().fromJson(jsonString, Array<SatelliteModel>::class.java).toList()

            emit(Resource.Success(satelliteList))

        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun fetchSatelliteDetail(context: Context,id: Int): Flow<Resource<SatelliteDetailModel>> = flow {
        emit(Resource.Loading())
        try {
            val localList = dao.getSatelliteDetailList()
            val filteredList = localList.filter { it.id == id }
            if (filteredList.isNotEmpty()) {
                val model = SatelliteDetailEntityMapper.asDomain(filteredList.first())
                emit(Resource.Success(model))
            } else {
                val jsonString = readAssetFile(context, "SATELLITE-DETAIL.json")
                val satelliteDetailList: List<SatelliteDetailModel> =
                    Gson().fromJson(jsonString, Array<SatelliteDetailModel>::class.java).toList()

                val filteredDetailList = satelliteDetailList.filter { it.id == id }

                if (filteredDetailList.isNotEmpty()) {
                    val entity = SatelliteDetailEntityMapper.asEntity(filteredDetailList.first())
                    dao.insertSatelliteDetail(entity)
                    emit(Resource.Success(filteredDetailList.first()))
                } else {
                    emit(Resource.Error("Detay Bulunamadı"))
                }
            }

        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun fetchSatellitePosition(context: Context,id: Int): Flow<Resource<List<PositionModel>>> = flow {
        emit(Resource.Loading())
        try {

            val jsonString = readAssetFile(context,"POSITIONS.json")
            val satellitePositionList: PositionListModel = Gson().fromJson(jsonString, PositionListModel::class.java)
            val filteredList = satellitePositionList.list.filter { it.id == id.toString() }

            if (filteredList.isNotEmpty()) {
                val positionList = filteredList.first().positions

                if (positionList.isNotEmpty()) {
                    emit(Resource.Success(positionList))
                } else {
                    emit(Resource.Error("Pozisyonlar Bulunamadı"))
                }
            } else {
                emit(Resource.Error("Pozisyonlar Bulunamadı"))
            }

        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
        }
    }


}