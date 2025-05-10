package com.example.jamplayer.presentation.common.model

data class AudioItemUi(
    val id: Long = 0,
    val title: String = "",
    val data: String = "",
    val duration: String = "",
    val size: String = "",
    val album: String? = null,
    val artist: String? = null,
    val thumbnail: String? = null,
)
