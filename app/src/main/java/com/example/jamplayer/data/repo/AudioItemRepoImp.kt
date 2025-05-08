package com.example.jamplayer.data.repo

import com.example.jamplayer.data.local.roomdb.dao.AudioItemDao
import com.example.jamplayer.data.local.roomdb.table.AudioItem
import com.example.jamplayer.presentation.repo.AudioItemRepo
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