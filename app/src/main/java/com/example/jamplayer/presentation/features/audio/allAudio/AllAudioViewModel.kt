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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class AllAudioViewModel @Inject constructor(
    private val audioItemRepo: AudioItemRepo,
    private val fetchAudioMedia: FetchAudioMedia,
    private val exoPlayer: ExoPlayer,
) : ViewModel() {


    val play = exoPlayer.play()
    val pause = exoPlayer.pause()
    fun nextSong() = exoPlayer.seekToNext()
    fun previousSong() = exoPlayer.seekToPrevious()
    val isPlaying = exoPlayer.isPlaying
    val isLoading = exoPlayer.isLoading


    private val _uiState = MutableStateFlow(AllAudioUiState())
    val uiState = _uiState.asStateFlow()


    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = true
                    )
                    Log.e("onPlaybackStateChanged", "State_Buffering")
                }

                Player.STATE_READY -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false
                    )
                    exoPlayer.play()
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
            _uiState.value = uiState.value.copy(
                isPlaying = isPlaying
            )
        }

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


    fun startSong(songUri: List<String>, index: Int) {
        val mediaItems = songUri.map { MediaItem.fromUri(it) }
        exoPlayer.setMediaItems(mediaItems, index, 0L)
        exoPlayer.prepare()
    }



    fun insertAudioItem() {
        viewModelScope.launch {
            fetchAudioMedia.fetchAudioMedia().forEach {
                audioItemRepo.insertAudioItem(
                    AudioItem(
                        id = it.id,
                        title = it.title,
                        data = it.data,
                        duration = it.duration,
                        size = it.size,
                        album = it.album,
                        artist = it.artist,
                        thumbnail = it.thumbnail.toString()
                    )
                )
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