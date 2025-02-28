package com.example.presentation.ui.detail

import com.example.domain.use_cases.SatelliteDetailUseCase
import com.example.domain.use_cases.SatelliteListUseCase
import com.example.domain.use_cases.SatellitePositionUseCase
import com.saidatmaca.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val satelliteDetailUseCase: SatelliteDetailUseCase,
    private val satellitePositionUseCase: SatellitePositionUseCase
) : BaseViewModel(){


}