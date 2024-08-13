package com.group4.fitconnect.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.CircularProgressIndicator
import com.group4.fitconnect.R
import com.group4.fitconnect.viewmodel.HealthViewModel


@Composable
fun StepProgress(currentSteps: Int) {
    val healthViewModel: HealthViewModel = hiltViewModel()
    val goal = healthViewModel.goal.value
    val progress = (currentSteps/goal).toFloat()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.border_blue),
                        colorResource(id = R.color.accent)
                    )
                )
            ),
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(Color.Transparent),
            progress = progress,
            strokeWidth = 8.dp,
            indicatorColor = colorResource(id = R.color.white)
        )
        SensorDetails(healthViewModel, currentSteps)
    }
}