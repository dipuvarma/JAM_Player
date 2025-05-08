package com.example.jamplayer.presentation.features.audio.allVideo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jamplayer.data.local.roomdb.table.AudioItem
import com.example.jamplayer.data.system.mediaStore.FetchAudioMedia
import com.example.jamplayer.presentation.repo.AudioItemRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AllVideoViewModel @Inject constructor(
    private val audioItemRepo: AudioItemRepo,
    private val fetchAudioMedia: FetchAudioMedia,
) : ViewModel() {


    fun insertAudioItem() {
        viewModelScope.launch {
            fetchAudioMedia.fetchAudioMedia().forEach {
                Log.d("TAG", "insertAudioItem: ${it.data}")
                audioItemRepo.insertAudioItem(
                    AudioItem(
                        id = it.id,
                        title = it.title,
                        data = it.data,
                        duration = it.duration,
                        size = it.size,
                        album = it.album ,
                        artist = it.artist,
                        thumbnail = it.thumbnail.toString()
                    )
                )
            }
        }
    }
}