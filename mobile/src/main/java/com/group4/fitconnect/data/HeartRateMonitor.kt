package com.group4.fitconnect.data

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class HeartRateMonitor @Inject constructor() {
    private val dataPoints = MutableSharedFlow<Int>(extraBufferCapacity = 10)

    fun receive(): SharedFlow<Int> = dataPoints.asSharedFlow()

    fun send(hr: Int) {
        dataPoints.tryEmit(hr)
    }
}