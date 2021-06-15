package com.learntodroid.bottomnavigationtutorial

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val service: EpisodeApi,
    private val db: AppDB
) : EpisodeRepository {

    @ExperimentalPagingApi
    override fun getAllEpisodes(): Flow<PagingData<Episode>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        remoteMediator = EpisodeRemoteMediator(service, db)
    ) {
        db.episodeDao().pagingSource()
    }.flow

    @ExperimentalPagingApi
    override fun getSearchEpisodes(string: String?): Flow<PagingData<Episode>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        remoteMediator = EpisodeRemoteMediator(service, db)
    ) {
        db.episodeDao().pagingSource()
    }.flow


}