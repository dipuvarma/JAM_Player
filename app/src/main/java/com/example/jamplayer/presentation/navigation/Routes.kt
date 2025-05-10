package com.example.jamplayer.presentation.navigation

import android.net.Uri
import kotlinx.serialization.Serializable


@Serializable
object AllVideo

@Serializable
data class VideoPlayer(
    val videoUri: String,
)

