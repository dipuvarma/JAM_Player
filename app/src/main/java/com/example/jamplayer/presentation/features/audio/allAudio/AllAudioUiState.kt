package com.example.jamplayer.presentation.features.audio.allAudio

import com.example.jamplayer.presentation.common.model.AudioItemUi

data class AllAudioUiState(
    val allAudio: List<AudioItemUi> = emptyList(),
    val isLoading: Boolean = false,
    val isPlaying: Boolean = false
)
