package com.example.jamplayer.di

import android.content.Context
import com.example.jamplayer.data.system.mediaStore.FetchAudioMedia
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


}

