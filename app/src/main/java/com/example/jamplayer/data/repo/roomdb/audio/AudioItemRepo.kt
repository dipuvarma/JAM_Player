package com.example.jamplayer.data.repo.roomdb.audio

import com.example.jamplayer.data.datasource.local.roomdb.table.AudioItem
import kotlinx.coroutines.flow.Flow

interface AudioItemRepo {

    suspend fun insertAudioItem(audioItem: AudioItem)

    fun getAudioItems(): Flow<List<AudioItem>>

}