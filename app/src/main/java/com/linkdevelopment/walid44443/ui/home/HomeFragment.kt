package com.linkdevelopment.walid44443.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.linkdevelopment.walid44443.R
import com.linkdevelopment.walid44443.base.BaseFragment
import com.linkdevelopment.walid44443.databinding.FragmentHomeBinding
import com.linkdevelopment.walid44443.models.response.Article
import com.linkdevelopment.walid44443.util.callback.OnItemAdapterClicked
import com.weightwatchers.ww_exercise_01.base.createViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    OnItemAdapterClicked<Article> {

    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory =
        createViewModelFactory { HomeViewModel() }

    private val itemsAdapter: ArticlesListAdapter = ArticlesListAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.onEach { observeState(it) }.launchIn(lifecycleScope)
        binding.rvArticles.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvArticles.adapter = itemsAdapter


        // start call api
        lifecycleScope.launchWhenCreated {
            viewModel.intents.send(HomeIntents.GetData)
        }

        binding.tryAgainBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intents.send(HomeIntents.GetData)
            }
        }
        binding.refreshList.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.intents.send(HomeIntents.GetData)
            }
        }
    }

    private fun observeState(state: HomeViewState) {
        if (isBindingInitialized()) {
            binding.refreshList.isRefreshing = state.isLoading
            binding.loadingAnimationView.visibility =
                if (state.isLoading) View.VISIBLE else View.GONE
            binding.networkAnimationView.visibility =
                if (state is HomeViewState.Error) View.VISIBLE else View.GONE

            binding.tryAgainBtn.visibility =
                if (state.articleList != null || state.isLoading) View.GONE else View.VISIBLE


            when (state) {
                HomeViewState.Idle -> {

                }
                HomeViewState.Loading -> {
                    binding.rvArticles.visibility = View.GONE
                    binding.emptyTv.visibility = View.GONE
                    binding.loadingAnimationView.visibility = View.VISIBLE
                }
                HomeViewState.EmptyArticlesList -> {
                    binding.emptyTv.visibility = View.VISIBLE
                    binding.rvArticles.visibility = View.GONE

                }
                is HomeViewState.Error -> {
                    binding.rvArticles.visibility = View.GONE
                    state.errorMsg?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.retry), View.OnClickListener {
                                lifecycleScope.launch {
                                    viewModel.intents.send(HomeIntents.GetData)
                                }
                            })
                            .show()
                    }
                    binding.networkAnimationView.visibility = View.VISIBLE
                }
                is HomeViewState.SuccessGetArticlesList -> {
                    binding.rvArticles.visibility = View.VISIBLE
                    itemsAdapter.submitList(state.articleList)
                }

                is HomeViewState.ArticleClick -> {
                    if (findNavController().currentDestination?.id != R.id.nav_article_details)
                        findNavController().navigate(
                            R.id.nav_article_details, Bundle().apply {
                                this.putParcelable(ARTICLE_OBJECT, state.clickedArticle)
                            }
                        )
                }
            }
            lifecycleScope.launch {
                viewModel.intents.send(HomeIntents.GoIdle)
            }
        }
    }

    override fun onClickListener(item: Article?) {
        item?.let {
            lifecycleScope.launch {
                viewModel.intents.send(HomeIntents.OnArticleClick(it))
            }
        }
    }

    companion object Data {
        const val ARTICLE_OBJECT = "ARTICLE_OBJECT"
    }
}