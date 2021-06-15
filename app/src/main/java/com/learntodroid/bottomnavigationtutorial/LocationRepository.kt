package com.learntodroid.bottomnavigationtutorial

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getAllLocations(): Flow<PagingData<Location>>
}