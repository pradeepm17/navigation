package com.learntodroid.bottomnavigationtutorial

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<Episode>)


    @Query("SELECT * FROM episodes")
    fun pagingSource(): PagingSource<Int, Episode>

    @Query("DELETE FROM episodes")
    suspend fun clearAll()

 /*   @Query("SELECT * FROM episodes WHERE  username LIKE '%' || :query  || '%' ORDER BY activate DESC , username ASC , linked DESC")
    fun loadAllUsersQuery( query: String?): PagingSource<Int, Episode>
*/
}