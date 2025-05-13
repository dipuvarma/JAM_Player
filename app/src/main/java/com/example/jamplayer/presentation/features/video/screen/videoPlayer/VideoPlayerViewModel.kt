package com.example.jamplayer.presentation.features.video.screen.videoPlayer

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.jamplayer.di.ExoPlayerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val playerFactory: ExoPlayerFactory
) : ViewModel() {

    var exoPlayer: ExoPlayer = playerFactory.create()
        private set

    private val _isBuffering = MutableStateFlow(false)
    val isBuffering: StateFlow<Boolean> = _isBuffering

    private var isReleased = false

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
        }

        override fun onIsLoadingChanged(isLoading: Boolean) {
            _isBuffering.value = isLoading
        }
    }

    init {
        exoPlayer.addListener(playerListener)
    }

    fun playVideo(videoUri: Uri) {
        if (isReleased) {
            // Rebuild a new ExoPlayer instance
            exoPlayer = playerFactory.create()
            exoPlayer.addListener(playerListener)
            isReleased = false
        }

        exoPlayer.stop()
        exoPlayer.clearMediaItems()
        val mediaItem = MediaItem.fromUri(videoUri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    fun releasePlayer() {
        if (isReleased) return
        exoPlayer.removeListener(playerListener)
        exoPlayer.release()
        isReleased = true
    }


    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }

    //    fun seekBackward(ms: Long = 15_000) {
//        if (!exoPlayer.isPlaying) return
//        val position = exoPlayer.currentPosition - ms
//        exoPlayer.seekTo(position.coerceAtLeast(0))
//    }
//    fun seekForward(ms: Long = 30_000) {
//        if (!exoPlayer.isPlaying) return
//        val position = exoPlayer.currentPosition + ms
//        exoPlayer.seekTo(position.coerceAtMost(exoPlayer.duration))
//    }
}



