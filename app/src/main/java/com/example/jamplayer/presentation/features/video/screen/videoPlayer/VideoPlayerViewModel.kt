package com.example.jamplayer.presentation.features.video.screen.videoPlayer

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
     val exoPlayer: ExoPlayer
) : ViewModel() {

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            // You don't need to call play()/pause() here – it's already triggered by UI or player
            Log.d("VideoPlayerViewModel", "isPlaying changed: $isPlaying")
        }
    }

    init {
        exoPlayer.addListener(playerListener)
    }

    fun playVideo(videoUri: Uri) {
        val mediaItem = MediaItem.fromUri(videoUri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true // ✅ this is the correct way to auto-play
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.removeListener(playerListener)
        exoPlayer.release() // ✅ avoid memory leaks
    }
}
