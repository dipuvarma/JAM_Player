package com.example.jamplayer.presentation.features.audio.audioPlayer

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jamplayer.presentation.features.audio.allAudio.AllAudioViewModel
import com.example.jamplayer.utils.formatTime
import kotlinx.coroutines.delay

@Composable
fun AudioPlayerScreen(viewModel: AllAudioViewModel) {

    val uiState = viewModel.uiState.collectAsState()
    val isPlaying = viewModel.isPlayingState.collectAsState()

    // Current song duration and position
    var currentPosition by remember { mutableLongStateOf(0L) }
    val duration = viewModel.exoPlayer.duration.coerceAtLeast(1L)

    // Playback position tracker
    LaunchedEffect(Unit) {
        while (true) {
            currentPosition = viewModel.exoPlayer.currentPosition
            delay(500)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // Optional: Track title display
        uiState.value.allAudio.getOrNull(viewModel.exoPlayer.currentMediaItemIndex)?.let { track ->
           Spacer(modifier = Modifier.height(32.dp))
            Column {
                Text(text = "🎵 ${track.title}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "🎤 ${track.artist}", style = MaterialTheme.typography.bodyMedium)
            }
        }


        Text("Now Playing", style = MaterialTheme.typography.titleMedium)



        // Seek bar
        Column {
            Slider(
                value = currentPosition.toFloat() / duration,
                onValueChange = { fraction ->
                    currentPosition = (fraction * duration).toLong()
                },
                onValueChangeFinished = {
                    viewModel.exoPlayer.seekTo(currentPosition)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Time labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(formatTime(currentPosition))
                Text(formatTime(duration))
            }
        }

        // Play/Pause Button
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            IconButton(onClick = viewModel::previousSong) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "Previous")
            }
            IconButton(
                onClick = {
                    if (isPlaying.value) viewModel.pause()
                    else viewModel.play()
                }
            ) {
                Icon(
                    imageVector = if (isPlaying.value) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play or Pause",
                    modifier = Modifier.size(48.dp)
                )
            }
            IconButton(onClick = viewModel::nextSong) {
                Icon(Icons.Default.SkipNext, contentDescription = "Next")
            }
        }

        if (uiState.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
    }
}
