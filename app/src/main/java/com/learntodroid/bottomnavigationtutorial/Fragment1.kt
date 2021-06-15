package com.learntodroid.bottomnavigationtutorial


import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingSource
import com.learntodroid.bottomnavigationtutorial.databinding.FragmentFragment1Binding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_fragment1.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class Fragment1 : BaseFragment<FragmentFragment1Binding, EpisodesViewModel>() {
    private val episodesViewModel: EpisodesViewModel by viewModels()

    @Inject
    lateinit var episodeAdapter: EpisodeAdapter

    override val layoutId: Int
        get() = R.layout.fragment_fragment1

    override fun getVM(): EpisodesViewModel = episodesViewModel

    override fun bindVM(binding: FragmentFragment1Binding, vm: EpisodesViewModel) = with(binding) {
        with(episodeAdapter) {
            swipeRefresh.setOnRefreshListener { refresh() }
            rvEpisodes.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )
            with(vm) {
                launchOnLifecycleScope {
                    episodesFlow.collectLatest { submitData(it) }
                }
                launchOnLifecycleScope {
                    loadStateFlow.collectLatest {
                        swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                    }
                }
                launchOnLifecycleScope {
                    loadStateFlow.distinctUntilChangedBy { it.refresh }
                        .filter { it.refresh is LoadState.NotLoading }
                        .collect { rvEpisodes.scrollToPosition(0) }
                }
            }
        }



        search.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

              //  episodesViewModel.searchByTitle(search.text.toString())
              //  if (search.text.length > 0)
                   // Search(search.text.trim({ it <= ' ' }))
            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun Search(string: String?) {
        val filteredModelList:  PagingSource<Int, Episode>
  //      filteredModelList = FilterList(string!!)
    }

   /* private fun FilterList(query: String): PagingSource<Int, Episode> {
        return loadAllUsersQuery( query)
    }
*/

 /*   fun loadAllUsersQuery( query: String?): PagingSource<Int, Episode> {
        return try {
            Observable.create(ObservableOnSubscribe< PagingSource<Int, Episode> > { subscriber ->
                try {
                    val usersModel: PagingSource<Int, Episode> =
                       AppDB.getDatabase(activity!!).episodeDao()
                            .loadAllUsersQuery( query)
                    if (usersModel != null) subscriber.onNext(usersModel) else subscriber.onError(
                        NullPointerException("The value is Null")
                    )
                    subscriber.onComplete()
                } catch (e: Exception) {
                    subscriber.onError(e)
                }
            } as ObservableOnSubscribe<PagingSource<Int, Episode>>).subscribeOn(Schedulers.computation())
                .firstElement().blockingGet()
        } catch (e: Exception) {
            return  PagingSource<Int, Episode>;
        }
       //     java.lang.NullPointerException e;
        //   ArrayList< PagingSource<Int, Episode>>()
         //   return PagingSource<Int, Episode>;

    }*/
}