package com.example.presentation.ui.detail

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.use_cases.SatelliteDetailUseCase
import com.example.domain.use_cases.SatellitePositionUseCase
import com.example.model.PositionModel
import com.example.model.SatelliteDetailModel
import com.saidatmaca.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val satelliteDetailUseCase: SatelliteDetailUseCase,
    private val satellitePositionUseCase: SatellitePositionUseCase
) : BaseViewModel(){

    private val _uiState = MutableStateFlow<DetailUIState>(DetailUIState.Loading)
    val uiState: StateFlow<DetailUIState> = _uiState

    private val _currentPositionState = MutableStateFlow<PositionModel>(PositionModel(0.0,0.0))
    val currentPositionState: StateFlow<PositionModel> = _currentPositionState

    fun setCurrentPosition(position : PositionModel){
        _currentPositionState.value = position
    }

    fun fetchSatelliteDetail(context: Context,id:Int) {
        job = viewModelScope.launch {
            satelliteDetailUseCase.fetchSatelliteDetail(context,id)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                _uiState.value = DetailUIState.Success(it)
                            }
                        }
                        is Resource.Error -> {
                            _uiState.value = DetailUIState.Error(result.message ?: "Unknown error")
                        }
                        is Resource.Loading -> {
                            _uiState.value = DetailUIState.Loading
                            delay(500)
                        }
                    }
                }.launchIn(this)
        }
    }

    fun fetchSatellitePositions(context: Context,id:Int) {
        job = viewModelScope.launch {
            satellitePositionUseCase.fetchSatellitePositions(context,id)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                emitPositionsPeriodically(it)
                            }
                        }
                        is Resource.Error -> {
                            Log.e("positionLog2","")
                        }
                        is Resource.Loading -> {
                            Log.e("positionLog3","")
                        }
                    }
                }.launchIn(this)
        }
    }
    fun emitPositionsPeriodically(list: List<PositionModel>){
        viewModelScope.launch {
            while (true) {
                list.forEachIndexed { index, positionModel ->
                    setCurrentPosition(positionModel)
                    delay(3000)
                }
            }
        }
    }
}
sealed interface DetailUIState{
    data class Success(val detail: SatelliteDetailModel): DetailUIState
    data object Loading :DetailUIState
    data class Error(val message: String) : DetailUIState
}