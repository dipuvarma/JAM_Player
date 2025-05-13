package com.example.jamplayer.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jamplayer.presentation.common.component.BottomBarNavigation
import com.example.jamplayer.presentation.common.component.PlayerTopAppBar
import com.example.jamplayer.presentation.features.audio.allAudio.AllAudioScreen
import com.example.jamplayer.presentation.features.profile.ProfileScreen
import com.example.jamplayer.presentation.features.setting.SettingScreen
import com.example.jamplayer.presentation.features.video.screen.allVideo.AllVideoScreen
import com.example.jamplayer.presentation.navigation.Graph
import com.example.jamplayer.presentation.navigation.Graph.AllAudio
import com.example.jamplayer.presentation.navigation.Graph.AllVideo
import com.example.jamplayer.utils.bottomNavItemList

@Composable
fun MainPlayerScreen(
    rootNavController: NavController,
    tabNavController: NavHostController
) {
    Scaffold(
        topBar = {
            PlayerTopAppBar(
                title = "JAM Player"
            )
        },
        bottomBar = {
            BottomBarNavigation(
                navController = tabNavController,
                items = bottomNavItemList
            )
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
                    allAudioViewModel = hiltViewModel(),
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
