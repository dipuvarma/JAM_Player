package com.example.jamplayer

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.jamplayer.ui.theme.JAMPlayerTheme
import com.example.jamplayer.presentation.features.JamPlayerApp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JAMPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    JamPlayerApp(innerPadding)
                }
            }
        }
    }
}

