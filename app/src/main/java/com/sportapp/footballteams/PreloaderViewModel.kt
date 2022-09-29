package com.sportapp.footballteams

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sportapp.footballteams.ui.utils.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PreloaderViewModel: ViewModel() {

    private var _status by mutableStateOf(Status.LOADING)
    val status
        get() = _status

    fun wait() = viewModelScope.launch {
        delay(5000)
        _status = Status.OK
    }
}