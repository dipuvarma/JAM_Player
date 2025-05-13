package com.example.jamplayer.presentation.navigation

import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
sealed class Graph {

    @Serializable
    object AllVideo

    @Serializable
    data class VideoPlayer(
        val videoUri: String,
    )

    @Serializable
    object AllAudio

    @Serializable
    object AudioPlayer

    @Serializable
    object PlayerProfile

    @Serializable
    object PlayerSetting

    @Serializable
    object MainPlayer

}



