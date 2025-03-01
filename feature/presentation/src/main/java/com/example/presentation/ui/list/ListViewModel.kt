package com.example.presentation.ui.list

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.use_cases.SatelliteListUseCase
import com.example.model.SatelliteModel
import com.saidatmaca.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val satelliteListUseCase: SatelliteListUseCase
) : BaseViewModel (){

    private val _uiState = MutableStateFlow<ListUIState>(ListUIState.Loading)
    val uiState: StateFlow<ListUIState> = _uiState

    fun updateSearchQuery(query: String) {
        val currentState = _uiState.value
        if (currentState is ListUIState.Success) {
            _uiState.update {
                currentState.copy(
                    searchQuery = query,
                    filteredList = if (query.length >= 3) {
                        filterList(currentState.satelliteList, query)
                    } else {
                        currentState.satelliteList
                    }
                )
            }
        }
    }

    private fun filterList(list: List<SatelliteModel>, query: String): List<SatelliteModel> {
        return if (query.isEmpty()) {
            list
        } else {
            list.filter { satellite ->
                satellite.name.contains(query, ignoreCase = true)
            }
        }
    }

    fun fetchSatelliteList(context: Context) {
        job = viewModelScope.launch {
            satelliteListUseCase.fetchSatelliteList(context)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                _uiState.value = ListUIState.Success(it)
                            }
                        }
                        is Resource.Error -> {
                            _uiState.value = ListUIState.Error(result.message ?: "Unknown error")
                        }
                        is Resource.Loading -> {
                            _uiState.value = ListUIState.Loading
                            delay(500)
                        }
                    }
                }.launchIn(this)
        }
    }
}

sealed interface ListUIState {
    data class Success(
        val satelliteList: List<SatelliteModel>,
        val searchQuery: String = "",
        val filteredList: List<SatelliteModel> = satelliteList
    ) : ListUIState

    data object Loading : ListUIState
    data class Error(val message: String) : ListUIState
}