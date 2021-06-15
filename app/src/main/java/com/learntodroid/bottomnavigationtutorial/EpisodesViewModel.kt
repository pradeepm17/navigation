package com.learntodroid.bottomnavigationtutorial

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class EpisodesViewModel @ViewModelInject constructor(
    private val episodeRepository: EpisodeRepository
)  : BaseViewModel() {

    private lateinit var _episodesFlow: Flow<PagingData<Episode>>
    val episodesFlow: Flow<PagingData<Episode>>
        get() = _episodesFlow

    init {
        getAllEpisodes()
    }

    private fun getAllEpisodes() = launchPagingAsync({
        episodeRepository.getAllEpisodes().cachedIn(viewModelScope)
    }, {
        _episodesFlow = it
    })


    private fun getSearchEpisodes(string: String?) = launchPagingAsync({
        episodeRepository.getAllEpisodes().cachedIn(viewModelScope)
    }, {
        _episodesFlow = it
    })


}