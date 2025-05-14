package com.example.jamplayer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jamplayer.presentation.MainPlayerScreen
import com.example.jamplayer.presentation.features.audio.allAudio.AllAudioViewModel
import com.example.jamplayer.presentation.features.audio.audioPlayer.AudioPlayerScreen
import com.example.jamplayer.presentation.features.video.screen.videoPlayer.VideoPlayerScreen
import com.example.jamplayer.presentation.navigation.Graph.AudioPlayer
import com.example.jamplayer.presentation.navigation.Graph.MainPlayer
import com.example.jamplayer.presentation.navigation.Graph.VideoPlayer

@Composable
fun JamPlayerNavGraph() {
    val rootNavController = rememberNavController()
    val tabNavController = rememberNavController() // ✅ Hoisted here


    val audioViewModel = hiltViewModel<AllAudioViewModel>()


    NavHost(
        navController = rootNavController,
        startDestination = MainPlayer
    ) {

        composable<MainPlayer> {
            MainPlayerScreen(
                rootNavController = rootNavController,
                tabNavController = tabNavController,
                audioViewModel,
            )
        }

        composable<AudioPlayer> {
            AudioPlayerScreen(viewModel = audioViewModel)
        }

        composable<VideoPlayer> {
            val arg = it.toRoute<VideoPlayer>()
            VideoPlayerScreen(
                viewModel = hiltViewModel(),
                videoUri = arg.videoUri
            )
        }
    }
}
