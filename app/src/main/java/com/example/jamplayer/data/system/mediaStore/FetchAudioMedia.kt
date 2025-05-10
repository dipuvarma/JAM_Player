package com.example.jamplayer.data.system.mediaStore

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.util.Log
import com.example.jamplayer.data.dto.AudioMediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FetchAudioMedia @Inject constructor (
    private val contentResolver: ContentResolver,
) {

    suspend fun fetchAudioMedia(): MutableList<AudioMediaItem> {

        val audioList = mutableListOf<AudioMediaItem>()

        withContext(Dispatchers.IO) {

            val projection = arrayOf(
                Media._ID,
                Media.DISPLAY_NAME,
                Media.DATA,
                Media.DURATION,
                Media.SIZE,
                Media.ALBUM,
                Media.ARTIST,
                Media.ALBUM_ID,
            )

            contentResolver.query(
                Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(Media._ID)
                val titleColumn = cursor.getColumnIndexOrThrow(Media.DISPLAY_NAME)
                val dataColumn = cursor.getColumnIndexOrThrow(Media.DATA)
                val durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION)
                val sizeColumn = cursor.getColumnIndexOrThrow(Media.SIZE)
                val albumColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)
                val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)
                val albumIdColumn = cursor.getColumnIndexOrThrow(Media.ALBUM_ID)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val data = cursor.getString(dataColumn)
                    val duration = cursor.getLong(durationColumn)
                    val size = cursor.getLong(sizeColumn)
                    val album = cursor.getString(albumColumn)
                    val artist = cursor.getString(artistColumn)
                    val albumId = cursor.getLong(albumIdColumn)

                    val thumbnailUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, albumId)
                    val song = AudioMediaItem(
                        id, title, data, duration, size, album, artist, thumbnailUri
                    )
                    audioList.add(song)
                }
            }
        }
        return audioList
    }
}