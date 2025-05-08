package com.example.jamplayer.data.local.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jamplayer.data.local.roomdb.table.AudioItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAudioItem(audioItem: AudioItem)

    @Query("SELECT * FROM audio ORDER BY duration ASC")
    fun getAudioItems(): Flow<List<AudioItem>>

}