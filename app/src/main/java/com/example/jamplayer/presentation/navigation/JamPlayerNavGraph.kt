package com.example.jamplayer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jamplayer.presentation.features.video.screen.allVideo.AllVideoScreen
import com.example.jamplayer.presentation.features.video.screen.videoPlayer.VideoPlayerScreen

@Composable
fun JamPlayerNavGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AllVideo){

        composable<AllVideo> {
            AllVideoScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
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