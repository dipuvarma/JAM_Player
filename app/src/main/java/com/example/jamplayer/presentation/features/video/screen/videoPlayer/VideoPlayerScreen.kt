package com.example.jamplayer.presentation.features.video.screen.videoPlayer

import android.net.Uri
import android.view.Surface
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(
    viewModel: VideoPlayerViewModel,
    videoUri: String,
) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = viewModel.exoPlayer
                useController = true
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )

    LaunchedEffect(videoUri) {
        viewModel.playVideo(videoUri.toUri())
    }
}
