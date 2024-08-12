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
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.group4.fitconnect.databinding.ActivityMainBinding
import com.group4.fitconnect.viewmodel.HealthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SensorEventListener {

    private val healthViewModel: HealthViewModel by viewModels()
    private lateinit var mBinding: ActivityMainBinding
    private var sensorManager: SensorManager? = null
    private var totalSteps = 0f
    private val previousTotalSteps = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(mBinding.root)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        mBinding.llShare.setOnClickListener {
            //Share data with friends
            share()
        }
    }

    override fun onResume() {
        super.onResume()

        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }


    }

    //Method to set the sensor data in UI
    private fun setSensorData(currentSteps: Int) {
        mBinding.tvHeartBit.text = healthViewModel.heartRate.toString()
        mBinding.tvStepCont.text = currentSteps.toString()
        mBinding.tvCaloriesBurned.text = (currentSteps * 0.045).toString()
        mBinding.circularProgress.setProgressWithAnimation(currentSteps.toFloat())

    }

    //Method to share the data with friends
    private fun share() {
        val textToSend = """Hey there! Here I am sharing my fitness data from my personal app Fit Connect.
                |Total step count is $totalSteps
                |Current heart bit is ${healthViewModel.heartRate}
                |Total calories burned is ${totalSteps * 0.045}
            """.trimMargin()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textToSend)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onSensorChanged(event: SensorEvent?) {
       if (event!= null){
           totalSteps = event.values[0]
           Log.e("TAG", "onSensorChanged: ${event.values[0]}")
           val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
           setSensorData(currentSteps)
       }else{
           Log.e("TAG", "onSensorChanged: event is null")
       }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}