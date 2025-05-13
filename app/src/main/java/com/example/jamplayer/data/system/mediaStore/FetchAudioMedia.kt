package com.example.jamplayer.data.system.mediaStore

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.util.Log
import com.example.jamplayer.data.dto.AudioMediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FetchAudioMedia @Inject constructor(
    private val contentResolver: ContentResolver,
) {

    suspend fun fetchAudioMedia(): Flow<List<AudioMediaItem>> = flow {

        val audioList = mutableListOf<AudioMediaItem>()

        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(
            Media._ID,
            Media.DISPLAY_NAME,
            Media.DURATION,
            Media.SIZE,
            Media.ARTIST,
        )

        contentResolver.query(
            collection,
            projection,
            null,
            null,
            null
        )?.use { cursor ->

            val idColumn = cursor.getColumnIndexOrThrow(Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(Media.SIZE)
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val duration = cursor.getLong(durationColumn)
                val size = cursor.getLong(sizeColumn)
                val artist = cursor.getString(artistColumn)

                val data: Uri = ContentUris.withAppendedId(collection, id)


                val song = AudioMediaItem(
                    id, title, data, duration, size, artist
                )
                audioList.add(song)
            }
        }
        emit(audioList)
    }.flowOn(Dispatchers.IO)
}