package com.example.jamplayer.presentation.repo

import com.example.jamplayer.data.dto.VideoMediaItem
import kotlinx.coroutines.flow.Flow


interface VideoItemRepo {

    suspend fun getAllVideo(): Flow<List<VideoMediaItem>>

}