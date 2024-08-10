package com.group4.fitconnect.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.group4.fitconnect.data.SensorData
import com.group4.fitconnect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var sensorData: SensorData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        sensorData = getSensorData()
        setSensorData(sensorData)


        mBinding.llShare.setOnClickListener {
            //Share data with friends
            share(sensorData)
        }
    }

    //Method to get the sensor data
    private fun getSensorData(): SensorData {
        return SensorData(0,0,0)
    }

    //Method to set the sensor data in UI
    private fun setSensorData(sensorData: SensorData) {
        sensorData.let {
            mBinding.apply {
                tvStepCont.text = it.steps.toString()
                tvHeartBit.text = it.heartBit.toString()
                tvCaloriesBurned.text = it.caloriesBurned.toString()
            }
        }
    }

    //Method to share the data with friends
    private fun share(data: SensorData) {
        val textToSend = """Hey there! Here I am sharing my fitness data from my personal app Fit Connect.
                |Total step count is ${data.steps}
                |Current heart bit is ${data.heartBit}
                |Total calories burned is ${data.caloriesBurned}
            """.trimMargin()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textToSend)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}