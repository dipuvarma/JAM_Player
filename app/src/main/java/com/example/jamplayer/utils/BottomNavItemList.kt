package com.example.jamplayer.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.VideoLibrary
import com.example.jamplayer.presentation.navigation.Graph.AllAudio
import com.example.jamplayer.presentation.navigation.Graph.AllVideo
import com.example.jamplayer.presentation.navigation.Graph.PlayerProfile
import com.example.jamplayer.presentation.navigation.Graph.PlayerSetting


val bottomNavItemList = listOf(
    BottomNavItem(
        title = "Video",
        selectedIcon = Icons.Filled.VideoLibrary,
        unSelectedIcon = Icons.Outlined.VideoLibrary,
        route = AllVideo
    ),
    BottomNavItem(
        title = "Audio",
        selectedIcon = Icons.Filled.Audiotrack,
        unSelectedIcon = Icons.Outlined.Audiotrack,
        route = AllAudio
    ),
    BottomNavItem(
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unSelectedIcon = Icons.Outlined.Person,
        route = PlayerProfile
    ),
    BottomNavItem(
        title = "Setting",
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
        route = PlayerSetting
    ),
)