package com.example.jamplayer.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jamplayer.presentation.common.component.BottomBarNavigation
import com.example.jamplayer.presentation.common.component.MiniPlayerBarComp
import com.example.jamplayer.presentation.common.component.PlayerTopAppBar
import com.example.jamplayer.presentation.features.audio.allAudio.AllAudioScreen
import com.example.jamplayer.presentation.features.audio.allAudio.AllAudioViewModel
import com.example.jamplayer.presentation.features.profile.ProfileScreen
import com.example.jamplayer.presentation.features.setting.SettingScreen
import com.example.jamplayer.presentation.features.video.screen.allVideo.AllVideoScreen
import com.example.jamplayer.presentation.navigation.Graph
import com.example.jamplayer.presentation.navigation.Graph.AllAudio
import com.example.jamplayer.presentation.navigation.Graph.AllVideo
import com.example.jamplayer.utils.bottomNavItemList
import kotlinx.coroutines.launch

@Composable
fun MainPlayerScreen(
    rootNavController: NavController,
    tabNavController: NavHostController,
    audioViewModel: AllAudioViewModel,
) {
    val hasBeenPlayed by audioViewModel.hasBeenPlayed.collectAsStateWithLifecycle()
    val state = audioViewModel.uiState.collectAsState()
    val audioList = state.value.allAudio

    val isPlaying by audioViewModel.isPlayingState.collectAsState()
    val scope = rememberCoroutineScope()

    val currentBackStackEntry = tabNavController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination


    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            audioViewModel.markPlayed()
        }
    }

    Scaffold(
        topBar = {
            PlayerTopAppBar(
                title = "JAM Player"
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (currentDestination?.route?.contains("AllAudio") == true) {
                    if (hasBeenPlayed) {
                        HorizontalDivider()
                        audioList.getOrNull(audioViewModel.exoPlayer.currentMediaItemIndex)
                            ?.let { track ->
                                MiniPlayerBarComp(
                                    title = track.title,
                                    subTitle = track.artist!!,
                                    isFavorite = false,
                                    isPlaying = isPlaying,
                                    onPlayPauseClick = {
                                        if (isPlaying) {
                                            audioViewModel.pause()
                                        } else {
                                            audioViewModel.play()
                                        }
                                    },
                                    onSurfaceClick = {
                                        scope.launch {
                                            rootNavController.navigate(Graph.AudioPlayer) {
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    }
                                )
                            }
                        HorizontalDivider()
                    }
                }

                BottomBarNavigation(
                    navController = tabNavController,
                    items = bottomNavItemList
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = AllVideo,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<AllVideo> {
                AllVideoScreen(
                    viewModel = hiltViewModel(),
                    navController = rootNavController, // still send main nav
                )
            }

            composable<AllAudio> {
                AllAudioScreen(
                    allAudioViewModel = audioViewModel,
                    navController = rootNavController,
                )
            }

            composable<Graph.PlayerProfile> {
                ProfileScreen()
            }

            composable<Graph.PlayerSetting> {
                SettingScreen()
            }
        }
    }
}
