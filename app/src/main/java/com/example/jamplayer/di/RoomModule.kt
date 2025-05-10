package com.example.jamplayer.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.example.jamplayer.data.local.roomdb.dao.AudioItemDao
import com.example.jamplayer.data.local.roomdb.database.AudioItemDatabase
import com.example.jamplayer.data.repo.AudioItemRepoImp
import com.example.jamplayer.data.system.mediaStore.FetchAudioMedia
import com.example.jamplayer.presentation.features.audio.allAudio.AllAudioViewModel
import com.example.jamplayer.presentation.repo.AudioItemRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AudioItemDatabase::class.java,
            "audio_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideAudioItemDao(audioItemDatabase: AudioItemDatabase) =
        audioItemDatabase.getAudioItemDao()

    @Provides
    @Singleton
    fun provideAudioRepoImp(audioItemDao: AudioItemDao): AudioItemRepo {
        return AudioItemRepoImp(audioItemDao)
    }

    @Provides
    @Singleton
    fun provideAllVideoViewModel(audioItemRepo: AudioItemRepo, fetchAudioMedia: FetchAudioMedia, exoPlayer: ExoPlayer ) =
        AllAudioViewModel(audioItemRepo, fetchAudioMedia, exoPlayer)

}