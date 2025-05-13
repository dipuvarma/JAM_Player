package com.example.jamplayer.presentation.features.audio.allAudio

import com.example.jamplayer.presentation.common.model.AudioItemUi

data class AllAudioUiState(
    val isLoading: Boolean = false,
    var isPlaying: Boolean = false,
    val allAudio: List<AudioItemUi> = emptyList()
)
