package com.example.jamplayer.presentation

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.jamplayer.presentation.features.audio.allAudio.AllAudioScreen
import com.example.jamplayer.presentation.navigation.JamPlayerNavGraph
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun JamPlayerApp(innerPadding: PaddingValues) {
    val context = LocalContext.current

    val audioPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_AUDIO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val videoPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_VIDEO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val audioPermissionState = rememberPermissionState(audioPermission)
    val videoPermissionState = rememberPermissionState(videoPermission)

    // STEP 1: Ask for audio permission first
    LaunchedEffect(audioPermissionState.status.isGranted) {
        if (!audioPermissionState.status.isGranted) {
            audioPermissionState.launchPermissionRequest()
        }
    }

    // STEP 2: Once audio granted, ask for video
    LaunchedEffect(audioPermissionState.status.isGranted, videoPermissionState.status.isGranted) {
        if (audioPermissionState.status.isGranted && !videoPermissionState.status.isGranted) {
            videoPermissionState.launchPermissionRequest()
        }
    }

    // PERMISSION RATIONALE UI
    @Composable
    fun PermissionRequestUI(
        rationale: String,
        buttonText: String,
        onClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = rationale)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onClick) {
                Text(buttonText)
            }
        }
    }

    // HANDLE AUDIO PERMISSION FIRST
    when {
        !audioPermissionState.status.isGranted -> {
            val rationale = if (audioPermissionState.status.shouldShowRationale) {
                "We need audio access to play music. Please grant permission."
            } else {
                "Audio permission is required. Please allow it in settings."
            }

            PermissionRequestUI(
                rationale = rationale,
                buttonText = "Grant Audio Permission",
                onClick = { audioPermissionState.launchPermissionRequest() }
            )
        }

        !videoPermissionState.status.isGranted -> {
            val rationale = if (videoPermissionState.status.shouldShowRationale) {
                "We need video access to show media previews. Please grant permission."
            } else {
                "Video permission is required. Please allow it in settings."
            }

            PermissionRequestUI(
                rationale = rationale,
                buttonText = "Grant Video Permission",
                onClick = { videoPermissionState.launchPermissionRequest() }
            )
        }

        else -> {
            // BOTH PERMISSIONS GRANTED
            JamPlayerNavGraph()
        }
    }
}

