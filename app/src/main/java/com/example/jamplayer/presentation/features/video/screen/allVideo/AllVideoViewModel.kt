package com.example.jamplayer.presentation.features.video.screen.allVideo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jamplayer.data.mapper.toVideoItemUi
import com.example.jamplayer.presentation.repo.VideoItemRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllVideoViewModel @Inject constructor(
    private val videoItemRepo: VideoItemRepo,
) : ViewModel() {


    private val _videoUiState = MutableStateFlow(AllVideoUiState())
    val videoUiState = _videoUiState.asStateFlow()

    init {
        getAllVideos()
    }

    fun getAllVideos() {
        viewModelScope.launch {
            videoItemRepo.getAllVideo().collectLatest { videoItemList ->
                val allVideoItem = videoItemList.map {
                    it.toVideoItemUi()
                }
                _videoUiState.value = videoUiState.value.copy(
                    isLoading = true,
                    allVideoItem = allVideoItem
                )
                _videoUiState.value = videoUiState.value.copy(
                    isLoading = false,
                )
            }
        }
    }
}