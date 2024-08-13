package com.group4.fitconnect.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group4.fitconnect.health.HealthServiceManager
import com.group4.fitconnect.health.MeasureMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(
    private val healthServiceManager: HealthServiceManager
) : ViewModel() {

    val goal: MutableState<Int> = mutableIntStateOf(0)
    val heartRate: MutableState<Double> = mutableDoubleStateOf(0.0)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getHeartRate() {
        viewModelScope.launch {
            healthServiceManager
                .heartRateMeasureFlow()
                .collect { measureMessage ->
                    when (measureMessage) {
                        is MeasureMessage.MeasureData -> {
                            val latestHeartRateValue = measureMessage.data.last().value
                            heartRate.value = latestHeartRateValue
                        }

                        is MeasureMessage.MeasureAvailability -> {
                            measureMessage.availability
                        }
                    }
                }
        }
    }
}