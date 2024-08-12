package com.group4.fitconnect.data

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class HeartRateMonitor @Inject constructor() {
    private val datapoints = MutableSharedFlow<Int>(extraBufferCapacity = 10)

    fun receive(): SharedFlow<Int> = datapoints.asSharedFlow()

    fun send(hr: Int) {
        datapoints.tryEmit(hr)
    }
}