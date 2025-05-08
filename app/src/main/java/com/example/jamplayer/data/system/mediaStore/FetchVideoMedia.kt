package com.example.jamplayer.data.system.mediaStore

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.jamplayer.data.dto.VideoMediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchVideoMedia(private val contentResolver: ContentResolver) {

    suspend fun fetchVideoMedia(): List<VideoMediaItem> {

        val videoList = mutableListOf<VideoMediaItem>()

        withContext(Dispatchers.IO) {
            val collection =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Video.Media.getContentUri(
                        MediaStore.VOLUME_EXTERNAL
                    )
                } else {
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                }
            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.SIZE,
            )

            contentResolver.query(
                collection,
                projection,
                null,
                null,
                "${MediaStore.Video.Media.DISPLAY_NAME} ASC"
            )?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                val resolutionColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

                while (cursor.moveToNext()) {

                    val id = cursor.getLong(idColumn)
                    val data = cursor.getString(dataColumn)
                    val title = cursor.getString(titleColumn)
                    val duration = cursor.getLong(durationColumn)
                    val resolution = cursor.getString(resolutionColumn)
                    val size = cursor.getLong(sizeColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    videoList.add(
                        VideoMediaItem(id, data, title, duration, resolution, size, contentUri)
                    )
                }
            }
        }
        return videoList
    }
}