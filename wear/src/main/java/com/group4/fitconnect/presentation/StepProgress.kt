package com.group4.fitconnect.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.CircularProgressIndicator
import com.group4.fitconnect.R
import com.group4.fitconnect.viewmodel.HealthViewModel


@Composable
fun StepProgress() {
    val healthViewModel: HealthViewModel = hiltViewModel()
    val steps = healthViewModel.steps.value
    val goal = healthViewModel.goal.value
    val progress = (steps/goal).toFloat()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(Color.Transparent),
            progress = progress,
            strokeWidth = 8.dp,
            indicatorColor = colorResource(id = R.color.border_blue)
        )
        SensorDetails(healthViewModel)
    }
}