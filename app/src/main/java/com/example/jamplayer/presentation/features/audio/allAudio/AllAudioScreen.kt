package com.example.jamplayer.presentation.features.audio.allAudio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jamplayer.presentation.features.audio.component.AudioItemCard
import com.example.jamplayer.presentation.navigation.Graph.AudioPlayer

@Composable
fun AllAudioScreen(
    allAudioViewModel: AllAudioViewModel,
    navController: NavController,
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
                .padding(horizontal = 16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "All Music",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            items(state.value.allAudio) { audioItem ->
                AudioItemCard(audioItem) { audioUri ->
                    val songList = state.value.allAudio
                    val startIndex = songList.indexOfFirst { it.data == audioUri }
                    if (startIndex != -1) {
                        allAudioViewModel.startPlaylistSong(
                            songList.map { it.data },
                            startIndex
                        )
                        navController.navigate(AudioPlayer)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


