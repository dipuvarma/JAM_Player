package com.example.jamplayer.presentation.features.video.screen.allVideo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jamplayer.presentation.common.component.PlayerTopAppBar
import com.example.jamplayer.presentation.features.video.component.VideoItemCard
import com.example.jamplayer.presentation.features.video.screen.videoPlayer.VideoPlayerScreen
import com.example.jamplayer.presentation.navigation.VideoPlayer

@Composable
fun AllVideoScreen(
    viewModel: AllVideoViewModel,
    navController: NavHostController,
) {
    val state = viewModel.videoUiState.collectAsState()
    val allVideos = state.value.allVideoItem

    Column(modifier = Modifier.fillMaxSize()) {
        PlayerTopAppBar(
            title = "JAM Player"
        )
        when {
            state.value.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.value.allVideoItem.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(allVideos) { videoItem ->
                        VideoItemCard(videoItem) { uri ->
                            //pass uri
                            navController.navigate(VideoPlayer(videoUri = uri))
                        }
                    }
                }
            }

            state.value.error.isNotEmpty() -> {
                Text(text = "Anything Error!")
            }
        }
    }
}