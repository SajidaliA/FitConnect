package com.group4.fitconnect.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckSensorPermission() {

    val permissionState = rememberPermissionState(android.Manifest.permission.BODY_SENSORS, onPermissionResult = { isGranted ->
        if (isGranted) {
            // permission already granted
        }
    })
    LaunchedEffect(key1 = permissionState) {
        if (permissionState.status.isGranted) {
            // permission granted
        } else {
            permissionState.launchPermissionRequest()
        }
    }
}
