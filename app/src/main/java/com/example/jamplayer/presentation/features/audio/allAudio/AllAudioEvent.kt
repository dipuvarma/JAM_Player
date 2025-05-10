package com.example.jamplayer.presentation.features.audio.allAudio


sealed class AllAudioEvent {
    data object Loading : AllAudioEvent()
    data class Audio(val audioUiState: AllAudioUiState) : AllAudioEvent()
    data class Error(val error: String) : AllAudioEvent()
}
