package com.example.jamplayer.data.dto

import android.net.Uri

data class AudioMediaItem(
    val id: Long,
    val title: String,
    val data: Uri,
    val duration: Long,
    val size: Long,
    val artist: String?,
)
