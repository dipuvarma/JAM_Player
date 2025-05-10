package com.example.jamplayer.data.mapper

import com.example.jamplayer.data.dto.VideoMediaItem
import com.example.jamplayer.data.local.roomdb.table.AudioItem
import com.example.jamplayer.presentation.common.model.AudioItemUi
import com.example.jamplayer.presentation.features.video.screen.allVideo.AllVideoItemUi
import com.example.jamplayer.utils.formatDateFromTimestamp
import com.example.jamplayer.utils.formatSize
import com.example.jamplayer.utils.millisToMinutesSeconds


fun AudioItem.toAudioItemUI(): AudioItemUi {
    return AudioItemUi(
        id = id,
        title = title,
        data = data,
        duration = millisToMinutesSeconds(duration),
        size = formatSize(size),
        album = album,
        artist = artist,
        thumbnail = thumbnail
    )
}


fun VideoMediaItem.toVideoItemUi(): AllVideoItemUi {
    return AllVideoItemUi(
        id = id,
        contentUri = contentUri.toString(),
        title = title!!,
        duration = millisToMinutesSeconds(duration!!),
        resolution = resolution.toString(),
        size = formatSize(size!!),
        dateAdded = formatDateFromTimestamp(dateAdded!!)
    )
}