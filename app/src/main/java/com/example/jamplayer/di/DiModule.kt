package com.example.jamplayer.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.example.jamplayer.data.repo.VideoItemRepoImp
import com.example.jamplayer.data.system.mediaStore.FetchAudioMedia
import com.example.jamplayer.data.system.mediaStore.FetchVideoMedia
import com.example.jamplayer.presentation.features.video.screen.allVideo.AllVideoViewModel
import com.example.jamplayer.presentation.repo.VideoItemRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DiModule {


    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context) =
        FetchAudioMedia(context.contentResolver)

    @Provides
    @Singleton
    fun provideExoPlayerInstance(@ApplicationContext context: Context) =
        ExoPlayer.Builder(context).build()

    @Provides
    @Singleton
    fun provideVideoMediaContext(@ApplicationContext context: Context) =
        FetchVideoMedia(context.contentResolver)

    @Provides
    @Singleton
    fun provideFetchVideoMedia(fetchVideoMedia: FetchVideoMedia): VideoItemRepo {
        return VideoItemRepoImp(fetchVideoMedia)
    }

    @Provides
    @Singleton
    fun provideAllVideoRepo(repo: VideoItemRepo) = AllVideoViewModel(repo)
}

