package com.example.jamplayer.data.datasource.local.roomdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jamplayer.data.datasource.local.roomdb.dao.AudioItemDao
import com.example.jamplayer.data.datasource.local.roomdb.table.AudioItem


@Database(entities = [AudioItem::class], version = 1)
abstract class AudioItemDatabase : RoomDatabase() {

    abstract fun getAudioItemDao(): AudioItemDao
}