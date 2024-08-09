package com.rubrics.fitconnect

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rubrics.fitconnect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private var stepCount: Int = 2451
    private var heartBeat: Int = 62
    private var caloriesBurned: Int = 52
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.tvStepCont.text = stepCount.toString()
        mBinding.tvHeartBit.text = heartBeat.toString()
        mBinding.tvCaloriesBurned.text = caloriesBurned.toString()

        mBinding.llShare.setOnClickListener {
            //Share data with friends
            share("""Hey there! Here I am sharing my fitness data from my personal app Fit Connect.
                |Total step count is $stepCount
                |Current heart bit is $heartBeat
                |Total calories burned is $caloriesBurned
            """.trimMargin())
        }
    }

    private fun share(textToSend: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textToSend)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}