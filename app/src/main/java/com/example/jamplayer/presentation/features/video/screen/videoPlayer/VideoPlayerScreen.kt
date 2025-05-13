package com.example.jamplayer.presentation.features.video.screen.videoPlayer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.Surface
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import androidx.core.net.toUri

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(
    viewModel: VideoPlayerViewModel,
    videoUri: String,
) {

    val context = LocalContext.current
   // val activity = context as? Activity

    val isBuffering by viewModel.isBuffering.collectAsState()

    LaunchedEffect(videoUri) {
        viewModel.playVideo(videoUri.toUri())
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.releasePlayer()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Log.d("TAG", "VideoPlayerScreen: $isBuffering")
        if (isBuffering) {
            // Buffering UI
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }

        } else {

            AndroidView(
                factory = {
                    PlayerView(context).apply {
                        player = viewModel.exoPlayer
                        useController = true
                        controllerShowTimeoutMs = 3000
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
//                        setOnTouchListener { _, event ->
//                            gestureDetector.onTouchEvent(event)
//                            false
//                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
            //            if (showVolumeIndicator) {
//                VolumeIndicator(volumeLevel)
//            }
//
//            if (showBrightnessIndicator) {
//                BrightnessIndicator(brightnessLevel)
//            }
        }
    }
}
//    var volumeLevel by remember { mutableIntStateOf(-1) }
//    var brightnessLevel by remember { mutableFloatStateOf(-1f) }
//
//    val showVolumeIndicator = volumeLevel >= 0
//    val showBrightnessIndicator = brightnessLevel >= 0f

// Hide volume/brightness indicators after timeout
//    LaunchedEffect(volumeLevel) {
//        if (volumeLevel >= 0) {
//            delay(3000)
//            volumeLevel = -1
//        }
//    }
//    LaunchedEffect(brightnessLevel) {
//        if (brightnessLevel >= 0f) {
//            delay(3000)
//            brightnessLevel = -1f
//        }
//    }


//    val gestureDetector = remember(context) {
//        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
//            override fun onDoubleTap(e: MotionEvent): Boolean {
//                val screenWidth = context.resources.displayMetrics.widthPixels
//                if (e.x < screenWidth / 2) {
//                    viewModel.seekBackward()
//                } else {
//                    viewModel.seekForward()
//                }
//                return true
//            }
//
//            override fun onScroll(
//                e1: MotionEvent?,
//                e2: MotionEvent,
//                distanceX: Float,
//                distanceY: Float,
//            ): Boolean {
//                e1 ?: return false
//
//                val screenWidth = context.resources.displayMetrics.widthPixels
//                val screenHeight = context.resources.displayMetrics.heightPixels
//                val deltaY = (e2.y - e1.y) / screenHeight
//
//                if (e1.x < screenWidth / 2) {
//                    // Brightness control
//                    val window = activity?.window
//                    val lp = window?.attributes
//                    val newBrightness = (lp?.screenBrightness ?: 0.5f) - deltaY
//                    lp?.screenBrightness = newBrightness.coerceIn(0.01f, 1f)
//                    window?.attributes = lp
//                    brightnessLevel = lp?.screenBrightness ?: -1f
//                } else {
//                    // Volume control
//                    val audioManager =
//                        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//                    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
//                    val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
//                    val deltaVolume = (deltaY * maxVolume).toInt()
//                    val newVolume = (currentVolume - deltaVolume).coerceIn(0, maxVolume)
//                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
//                    volumeLevel = ((newVolume.toFloat() / maxVolume) * 100).toInt()
//                }
//
//                return true
//            }
//        })
//    }


@Composable
private fun VolumeIndicator(volume: Int) {
    Box(
        modifier = Modifier
            .padding(end = 32.dp)
            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = "Volume",
                tint = Color.White
            )
            Text("$volume%", color = Color.White)
        }
    }
}

@Composable
private fun BrightnessIndicator(brightness: Float) {
    Box(
        modifier = Modifier
            .padding(start = 32.dp)
            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.BrightnessHigh,
                contentDescription = "Brightness",
                tint = Color.White
            )
            Text("${(brightness * 100).toInt()}%", color = Color.White)
        }
    }
}
