package com.example.jamplayer.presentation.features.video.screen.allVideo

sealed class AllVideoEvent {

    object Loading : AllVideoEvent()
    data class AllVideo(val allVideoUiState: AllVideoUiState): AllVideoEvent()
    data class Error(val message: String): AllVideoEvent()

}