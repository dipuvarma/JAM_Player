package com.example.jamplayer.data.repo.roomdb.video

import com.example.jamplayer.data.model.VideoMediaItem
import com.example.jamplayer.data.repo.mediaStore.video.FetchVideoMedia
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VideoItemRepoImp @Inject constructor(
    private val fetchVideoMedia: FetchVideoMedia,
) : VideoItemRepo {

    override suspend fun getAllVideo(): Flow<List<VideoMediaItem>> {
         return fetchVideoMedia.fetchVideoMedia()
    }

}