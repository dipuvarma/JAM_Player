package com.example.jamplayer.data.dto

import android.net.Uri

data class AudioMediaItem(
    val id: Long,
    val title: String,
    val data: String,
    val duration: Long,
    val size: Long,
    val album: String?,
    val artist: String?,
    val thumbnail: Uri?
)
