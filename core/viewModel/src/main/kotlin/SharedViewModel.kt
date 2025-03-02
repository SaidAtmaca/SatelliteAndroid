package com.saidatmaca.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel :ViewModel() {

    private val _idState = MutableStateFlow(0)
    val idState = _idState.asStateFlow()

    private val _nameState = MutableStateFlow("")
    val nameState = _nameState.asStateFlow()

    fun updateId(int:Int){
        _idState.value = int
    }
    fun updateNameState(string: String){
        _nameState.value=string
    }

    override fun onCleared() {
        super.onCleared()
        _idState.value = 0
    }
}

