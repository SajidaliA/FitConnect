package com.group4.fitconnect.presentation

import androidx.compose.runtime.Composable
import com.group4.fitconnect.presentation.theme.FitConnectTheme

//Main composable method to make UI and set sensor data
@Composable
fun WearApp() {
    FitConnectTheme {
        CheckSensorPermission()
        StepProgress()
    }
}
