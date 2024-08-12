package com.group4.fitconnect.data

import android.util.Log
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import com.group4.fitconnect.utils.Constant.HR_KEY
import com.group4.fitconnect.utils.Constant.HR_PATH
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DataLayerListenerService : WearableListenerService() {

    @Inject
    lateinit var heartRateMonitor: HeartRateMonitor

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            when (event.type) {
                DataEvent.TYPE_CHANGED -> {
                    event.dataItem.run {
                        if (uri.path?.compareTo(HR_PATH) == 0) {
                            val heartRate = DataMapItem.fromDataItem(this)
                                .dataMap.getInt(HR_KEY)
                            Log.d(
                                "DataLayerListenerService",
                                "New heart rate value received: $heartRate"
                            )
                            heartRateMonitor.send(heartRate)
                        }
                    }
                }

                DataEvent.TYPE_DELETED -> {
                    // DataItem deleted
                }
            }
        }
    }
}