package com.example.jamplayer.presentation.features

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jamplayer.presentation.navigation.JamPlayerNavGraph
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun JamPlayerApp(innerPadding: PaddingValues) {
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_VIDEO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val permissionState = rememberPermissionState(permission)

    LaunchedEffect(Unit) {
        // Automatically launch permission request on first composition
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    if (permissionState.status.isGranted) {
        JamPlayerNavGraph()
    } else {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            val rationale = if (permissionState.status.shouldShowRationale) {
                "This app needs access to your videos to display them."
            } else {
                "Please grant storage/video permission to use this feature."
            }
            Text(rationale)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { permissionState.launchPermissionRequest() }) {
                Text("Grant Permission")
            }
        }
    }
}
