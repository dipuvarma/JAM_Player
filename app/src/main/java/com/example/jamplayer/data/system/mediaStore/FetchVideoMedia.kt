package com.example.jamplayer.data.system.mediaStore

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.example.jamplayer.data.dto.VideoMediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class FetchVideoMedia(private val contentResolver: ContentResolver) {

    suspend fun fetchVideoMedia(): Flow<List<VideoMediaItem>> = flow {
        val videoList = mutableListOf<VideoMediaItem>()

        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.RESOLUTION,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.SIZE,
        )

        contentResolver.query(
            collection,
            projection,
            null,
            null,
            "${MediaStore.Video.Media.DATE_ADDED} DESC" // or DISPLAY_NAME ASC
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val resolutionColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)
            val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getStringOrNull(titleColumn) ?: "Unknown"
                val duration = cursor.getLongOrNull(durationColumn) ?: 0L
                val resolution = cursor.getStringOrNull(resolutionColumn) ?: "Unknown"
                val size = cursor.getLongOrNull(sizeColumn) ?: 0L
                val dateAdded = cursor.getLongOrNull(dateAddedColumn) ?: 0L

                val contentUri = ContentUris.withAppendedId(collection, id)
                videoList.add(
                    VideoMediaItem(id, contentUri, title, duration, resolution, size, dateAdded)
                )
            }
        }
        emit(videoList)
    }.flowOn(Dispatchers.IO)
}
