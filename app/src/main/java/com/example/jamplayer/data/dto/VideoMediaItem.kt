package com.example.jamplayer.data.dto

import android.net.Uri

data class VideoMediaItem(
    val id: Long,
    val data: String,
    val title: String,
    val duration: Long,
    val resolution: String?,
    val size: Long,
    val uri: Uri,

)

