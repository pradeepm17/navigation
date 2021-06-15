package com.learntodroid.bottomnavigationtutorial

import android.net.Uri
import androidx.paging.*
import androidx.room.withTransaction

class LocationPagingDataSource(private val service: LocationApi) :
    PagingSource<Int, Location>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
        val pageNumber = params.key ?: 1
     //   Log.e("pageNumber","pageNumber"+pageNumber)
        return try {

            val response = service.getAllLocations(pageNumber)
            val pagedResponse = response.body()
            val data = pagedResponse?.results

            var nextPageNumber: Int? = null

            if (pagedResponse?.pageInfo?.next != null) {
                val uri = Uri.parse(pagedResponse.pageInfo.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Location>): Int = 1
}
