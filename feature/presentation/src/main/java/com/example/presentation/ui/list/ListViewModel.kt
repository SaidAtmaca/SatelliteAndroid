package com.example.presentation.ui.list

import com.example.domain.use_cases.SatelliteListUseCase
import com.saidatmaca.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val satelliteListUseCase: SatelliteListUseCase
) : BaseViewModel (){


}