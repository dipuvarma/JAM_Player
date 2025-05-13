package com.example.jamplayer.data.local.roomdb.table

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio")
data class AudioItem(
    @PrimaryKey
    val id: Long,
    val title: String,
    val data: String,
    val duration: Long,
    val size: Long,
    val artist: String?,
)
