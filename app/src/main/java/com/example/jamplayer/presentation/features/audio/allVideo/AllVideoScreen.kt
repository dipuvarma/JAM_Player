package com.example.jamplayer.presentation.features.audio.allVideo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import javax.inject.Inject

@Composable
fun AllVideoScreen(
    allVideoViewModel: AllVideoViewModel = hiltViewModel(),
) {


    LaunchedEffect(
        key1 = Unit
    ) {
        allVideoViewModel.insertAudioItem()
    }
}