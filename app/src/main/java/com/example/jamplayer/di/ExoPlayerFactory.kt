package com.example.jamplayer.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoPlayerFactory @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun create(): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }
}
