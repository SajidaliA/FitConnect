package com.group4.fitconnect.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.group4.fitconnect.data.HeartRateMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(
    private val heartRateMonitor: HeartRateMonitor,
) : ViewModel() {

    var heartRate: MutableState<Double> = mutableStateOf(0.0)

    fun getHeartRate(){
        heartRateMonitor.receive()
    }
}