package com.example.jamplayer.presentation.features.audio.allAudio

import android.text.Layout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jamplayer.presentation.features.audio.component.AudioItemCard

@Composable
fun AllAudioScreen(
    allAudioViewModel: AllAudioViewModel = hiltViewModel(),
) {
    val state = allAudioViewModel.uiState.collectAsState()

    if (state.value.isLoading) {
        AnimatedVisibility(visible = state.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            items(state.value.allAudio) { audioItem ->
                AudioItemCard(audioItem) { clickedSongPath ->
                    val songList = state.value.allAudio
                    val startIndex = songList.indexOfFirst { it.data == clickedSongPath }
                    if (startIndex != -1) {
                        allAudioViewModel.startSong(songList.map { it.data }, startIndex)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}

