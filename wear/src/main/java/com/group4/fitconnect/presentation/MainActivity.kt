/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.group4.fitconnect.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.group4.fitconnect.data.SensorData

class MainActivity : ComponentActivity() {
    private lateinit var sensorData: SensorData
    private var progress = 0f
    private var isRunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)
        sensorData = getSensorData()
        setContent {
            WearApp(sensorData = sensorData, progress, isRunning)
        }
    }

    //Method to get the sensor data
    private fun getSensorData(): SensorData {
        return SensorData(0, 0, 0)
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp(SensorData(4568, 71, 14), 0.5f)
}