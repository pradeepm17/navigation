package com.learntodroid.bottomnavigationtutorial

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodeApi {
    @GET("episode/")
    suspend fun getAllEpisodes(@Query("page") page: Int): Response<PagedResponse<Episode>>
}