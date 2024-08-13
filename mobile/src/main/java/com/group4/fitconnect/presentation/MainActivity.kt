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
import com.group4.fitconnect.utils.Constant.MY_PREF
import com.group4.fitconnect.utils.Constant.PREV_STEPS
import com.group4.fitconnect.viewmodel.HealthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SensorEventListener {

    private val healthViewModel: HealthViewModel by viewModels()
    private lateinit var mBinding: ActivityMainBinding
    private var sensorManager: SensorManager? = null
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(mBinding.root)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        loadPrevSteps()
        setSensorData(0)
        mBinding.llShare.setOnClickListener {
            //Share data with friends
            share()
        }
        mBinding.btnReset.setOnClickListener {
            savePrevSteps()
        }
    }

    //Method to save previous steps
    private fun savePrevSteps() {
        previousTotalSteps = totalSteps
        mBinding.tvStepCont.text = 0.toString()
        val sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat(PREV_STEPS, previousTotalSteps)
        editor.apply()
    }

    //Method to load previous steps
    private fun loadPrevSteps(){
        val sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        previousTotalSteps = sharedPreferences.getFloat(PREV_STEPS, 0f)
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG", "onResume")
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
            Log.e("TAG", "registerListener")
        }
    }

    //Method to set the sensor data in UI
    private fun setSensorData(currentSteps: Int) {
        mBinding.tvHeartBit.text = healthViewModel.heartRate.value.toInt().toString()
        mBinding.tvStepCont.text = currentSteps.toString()
        mBinding.tvCaloriesBurned.text = (currentSteps * 0.045).toString()
        mBinding.circularProgress.setProgressWithAnimation(currentSteps.toFloat())

    }

    //Method to share the data with friends
    private fun share() {
        val textToSend = """Hey there! Here I am sharing my fitness data from my personal app Fit Connect.
                |Total step count is ${totalSteps.toInt()}
                |Current heart bit is ${healthViewModel.heartRate.value.toInt()}
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
           val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
           setSensorData(currentSteps)
       }else{
           Toast.makeText(this, "onSensorChanged: event is null", Toast.LENGTH_SHORT).show()
       }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Toast.makeText(this, "onAccuracyChanged: $accuracy", Toast.LENGTH_SHORT).show()
    }
}