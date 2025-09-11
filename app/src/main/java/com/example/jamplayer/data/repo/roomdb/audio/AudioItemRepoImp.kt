package com.example.jamplayer.data.repo.roomdb.audio

import com.example.jamplayer.data.datasource.local.roomdb.dao.AudioItemDao
import com.example.jamplayer.data.datasource.local.roomdb.table.AudioItem
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class AudioItemRepoImp @Inject constructor(
    private val audioItemDao: AudioItemDao,
) : AudioItemRepo {

    override suspend fun insertAudioItem(audioItem: AudioItem) {
        audioItemDao.insertAudioItem(audioItem)
    }

    override fun getAudioItems(): Flow<List<AudioItem>> {
        return audioItemDao.getAudioItems()
    }

}