package com.example.jamplayer.data.repo.roomdb.video

import com.example.jamplayer.data.model.VideoMediaItem
import kotlinx.coroutines.flow.Flow

interface VideoItemRepo {
    suspend fun getAllVideo(): Flow<List<VideoMediaItem>>
}