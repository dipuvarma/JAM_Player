package com.example.jamplayer.presentation.features.video.screen.allVideo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.jamplayer.presentation.features.video.component.VideoItemCard
import com.example.jamplayer.presentation.navigation.Graph.VideoPlayer

@Composable
fun AllVideoScreen(
    viewModel: AllVideoViewModel,
    navController: NavController,
) {
    val state = viewModel.videoUiState.collectAsState()
    val allVideos = state.value.allVideoItem

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
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "All Videos",

                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                items(allVideos) { videoItem ->
                    Spacer(modifier = Modifier.height(16.dp))

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
