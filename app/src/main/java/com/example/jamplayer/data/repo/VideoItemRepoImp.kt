package com.example.jamplayer.data.repo

import com.example.jamplayer.data.dto.VideoMediaItem
import com.example.jamplayer.data.system.mediaStore.FetchVideoMedia
import com.example.jamplayer.presentation.repo.VideoItemRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class VideoItemRepoImp @Inject constructor(
    private val fetchVideoMedia: FetchVideoMedia,
) : VideoItemRepo {

    override suspend fun getAllVideo(): Flow<List<VideoMediaItem>> {
         return fetchVideoMedia.fetchVideoMedia()
    }

}