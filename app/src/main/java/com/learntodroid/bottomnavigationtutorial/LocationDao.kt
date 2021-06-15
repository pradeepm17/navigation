package com.learntodroid.bottomnavigationtutorial

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<Location>)

    @Query("SELECT * FROM location")
    fun pagingSource(): PagingSource<Int, Location>


    @Query("DELETE FROM location")
    suspend fun clearAll()

}