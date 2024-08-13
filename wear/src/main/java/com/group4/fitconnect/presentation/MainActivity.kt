/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.group4.fitconnect.presentation

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.group4.fitconnect.R
import com.group4.fitconnect.presentation.theme.FitConnectTheme
import com.group4.fitconnect.utils.Constant.MY_PREF
import com.group4.fitconnect.utils.Constant.PREV_STEPS
import com.group4.fitconnect.viewmodel.HealthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    private var mCurrentSteps = mutableIntStateOf(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        loadPrevSteps()

        setContent {
            val currentSteps by remember { mCurrentSteps }
            val healthViewModel: HealthViewModel = hiltViewModel()
            healthViewModel.getHeartRate()
            val heartRate = healthViewModel.heartRate.value
            FitConnectWearApp(
                currentSteps = currentSteps,
                heartRate = heartRate.toInt(),
                shareClicked = {
                    shareData(heartRate.toInt())
                },
                resetClicked = {
                    mCurrentSteps.intValue = 0
                    savePrevSteps()
                }
            )
        }
    }

    private fun shareData(heartRate: Int) {
        val textToSend =
            """Hey there! Here I am sharing my fitness data from my personal app Fit Connect.
                |Total step count is ${mCurrentSteps.intValue}
                |Current heart bit is $heartRate
                |Total calories burned is ${mCurrentSteps.intValue * 0.045}
            """.trimMargin()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textToSend)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    //Method to save previous steps
    private fun savePrevSteps() {
        previousTotalSteps = totalSteps
        val sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat(PREV_STEPS, previousTotalSteps)
        editor.apply()
    }

    //Method to load previous steps
    private fun loadPrevSteps(){
        val sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        previousTotalSteps = sharedPreferences.getFloat(PREV_STEPS, 0f)
        mCurrentSteps.intValue = previousTotalSteps.toInt()
    }

    override fun onResume() {
        super.onResume()
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            Toast.makeText(this, getString(R.string.no_sensor), Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            totalSteps = event.values[0]
            Log.e("TAG", "onSensorChanged: ${event.values[0]}")
            mCurrentSteps.intValue = totalSteps.toInt() - previousTotalSteps.toInt()
        } else {
            Log.e("TAG", "onSensorChanged: event is null")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

@Composable
fun FitConnectWearApp(
    currentSteps: Int,
    heartRate: Int,
    shareClicked: () -> Unit,
    resetClicked: () -> Unit
) {
    FitConnectTheme {
        StepProgress(currentSteps)
        SensorDetails(currentSteps, heartRate, shareClicked, resetClicked)
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    FitConnectWearApp(1024, 72, {}) {}
}