package com.learntodroid.bottomnavigationtutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.learntodroid.bottomnavigationtutorial.databinding.FragmentFragment2Binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject


@AndroidEntryPoint
class Fragment2 : BaseFragment<FragmentFragment2Binding,  LocationsViewModel>() {
    private val locationsViewModel: LocationsViewModel by viewModels()

    @Inject
    lateinit var locationAdapter: LocationAdapter

    override val layoutId: Int
        get() = R.layout.fragment_fragment2

    override fun getVM(): LocationsViewModel = locationsViewModel

    override fun bindVM(binding: FragmentFragment2Binding, vm: LocationsViewModel) = with(binding) {
        with(locationAdapter) {
            swipeRefresh.setOnRefreshListener { refresh() }
            rvLocations.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )
            with(vm) {
                launchOnLifecycleScope {
                    locationsFlow.collectLatest { submitData(it) }
                }
                launchOnLifecycleScope {
                    loadStateFlow.collectLatest {
                        swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                    }
                }
                launchOnLifecycleScope {
                    loadStateFlow.distinctUntilChangedBy { it.refresh }
                        .filter { it.refresh is LoadState.NotLoading }
                        .collect { rvLocations.scrollToPosition(0) }
                }
            }
        }
    }
}