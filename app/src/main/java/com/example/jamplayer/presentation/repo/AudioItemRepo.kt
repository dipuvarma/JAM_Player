package com.example.jamplayer.presentation.repo

import com.example.jamplayer.data.local.roomdb.table.AudioItem
import kotlinx.coroutines.flow.Flow

interface AudioItemRepo {

    suspend fun insertAudioItem(audioItem: AudioItem)

    fun getAudioItems(): Flow<List<AudioItem>>

}