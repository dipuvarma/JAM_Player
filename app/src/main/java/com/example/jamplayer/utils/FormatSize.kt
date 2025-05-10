package com.example.jamplayer.utils

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun formatSize(sizeInBytes: Long): String {
    val kb = sizeInBytes / 1024.0
    val mb = kb / 1024.0

    return when {
        mb >= 1 -> String.format("%.2f MB", mb)
        kb >= 1 -> String.format("%.2f KB", kb)
        else -> "$sizeInBytes B"
    }
}
