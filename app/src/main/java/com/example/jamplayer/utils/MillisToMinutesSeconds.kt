package com.example.jamplayer.utils

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun millisToMinutesSeconds(durationMillis: Long): String {
    val totalSeconds = durationMillis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}


