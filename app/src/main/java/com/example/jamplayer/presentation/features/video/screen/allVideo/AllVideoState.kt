package com.example.jamplayer.presentation.features.video.screen.allVideo

import android.net.Uri

data class AllVideoUiState(
    val isLoading: Boolean = false,
    val allVideoItem: List<AllVideoItemUi> = emptyList(),
    val error: String = "",
)

data class AllVideoItemUi(
    val id: Long,
    val contentUri: String,
    val title: String = "",
    val duration: String = "",
    val resolution: String = "",
    val size: String = "",
    val dateAdded: String = "",
)
