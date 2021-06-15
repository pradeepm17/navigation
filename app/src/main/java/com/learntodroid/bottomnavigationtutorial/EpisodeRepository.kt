package com.learntodroid.bottomnavigationtutorial

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    fun getAllEpisodes(): Flow<PagingData<Episode>>

    fun getSearchEpisodes(string: String?): Flow<PagingData<Episode>>

}