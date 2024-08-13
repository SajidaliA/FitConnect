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
import androidx.wear.compose.material.CircularProgressIndicator
import com.group4.fitconnect.R

@Composable
fun StepProgress(currentSteps: Int) {
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.background)),
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(Color.Transparent),
            progress = (currentSteps/5000).toFloat(),
            strokeWidth = 8.dp,
            indicatorColor = colorResource(id = R.color.white)
        )
    }
}