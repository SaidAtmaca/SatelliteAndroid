package com.saidatmaca.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {
    var job : Job? = null
}