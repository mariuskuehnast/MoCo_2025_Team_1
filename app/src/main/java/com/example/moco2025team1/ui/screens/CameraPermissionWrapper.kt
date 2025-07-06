package com.example.moco2025team1.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun CameraPermissionWrapper(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val cameraPermission = Manifest.permission.CAMERA

    var hasCameraPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, cameraPermission) == PackageManager.PERMISSION_GRANTED)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(cameraPermission)
        }
    }

    if (hasCameraPermission) {
        content()
    } else {
        Text("Camera permission is required to attach images to your entry.")
    }
}