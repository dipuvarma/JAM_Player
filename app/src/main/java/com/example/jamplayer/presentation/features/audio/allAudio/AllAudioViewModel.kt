package com.example.jamplayer.presentation.features.audio.allAudio

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.jamplayer.data.local.roomdb.table.AudioItem
import com.example.jamplayer.data.mapper.toAudioItemUI
import com.example.jamplayer.data.system.mediaStore.FetchAudioMedia
import com.example.jamplayer.presentation.repo.AudioItemRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.map

@HiltViewModel
class AllAudioViewModel @Inject constructor(
    private val audioItemRepo: AudioItemRepo,
    private val fetchAudioMedia: FetchAudioMedia,
    val exoPlayer: ExoPlayer,
) : ViewModel() {

    private val _hasBeenPlayed = MutableStateFlow(false)
    val hasBeenPlayed: StateFlow<Boolean> = _hasBeenPlayed

    fun markPlayed() {
        _hasBeenPlayed.value = true
    }

    private val _uiState = MutableStateFlow(AllAudioUiState())
    val uiState = _uiState.asStateFlow()

    /*Exo Player*/
    private var _isPlaying = MutableStateFlow(false)
    val isPlayingState = _isPlaying.asStateFlow()

    fun play() = exoPlayer.play()
    fun pause() = exoPlayer.pause()
    fun nextSong() = exoPlayer.seekToNext()
    fun previousSong() = exoPlayer.seekToPrevious()

    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                    _uiState.update { it.copy(isLoading = true) }
                    Log.e("onPlaybackStateChanged", "State_Buffering")
                }

                Player.STATE_READY -> {
                    _uiState.update { it.copy(isLoading = false) }
                    exoPlayer.play()
                    _isPlaying.value = true
                    Log.e("onPlaybackStateChanged", "State_Ready")

                }

                Player.STATE_ENDED -> {
                    exoPlayer.seekToNext()
                    Log.e("onPlaybackStateChanged", "State_Ended")

                }

                Player.STATE_IDLE -> {
                    Log.e("onPlaybackStateChanged", "Idle_State")
                }
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            _isPlaying.value = isPlaying
            Log.d("TAG", "onIsPlayingChanged: $isPlaying")
        }

    }

    fun startPlaylistSong(songUri: List<String>, index: Int) {
        exoPlayer.stop()
        exoPlayer.clearMediaItems()
        val mediaItems = songUri.map { MediaItem.fromUri(it) }
        exoPlayer.setMediaItems(mediaItems, index, 0L)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true


    }

    init {
        exoPlayer.addListener(playerListener)
        getAllAudioItem()
        insertAudioItem()
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.removeListener(playerListener)
    }

    fun insertAudioItem() {
        viewModelScope.launch {
            fetchAudioMedia.fetchAudioMedia().distinctUntilChanged().collectLatest {
                it.forEach {
                    audioItemRepo.insertAudioItem(
                        AudioItem(
                            id = it.id,
                            title = it.title,
                            data = it.data.toString(),
                            duration = it.duration,
                            artist = it.artist,
                            size = it.size,
                        )
                    )
                }

            }
        }
    }

    fun getAllAudioItem() {
        viewModelScope.launch {
            audioItemRepo.getAudioItems()
                .distinctUntilChanged()
                .collect {
                    val allAudio = it.map {
                        it.toAudioItemUI()
                    }
                    _uiState.value = uiState.value.copy(
                        allAudio = allAudio
                    )
                }
        }
    }
}
