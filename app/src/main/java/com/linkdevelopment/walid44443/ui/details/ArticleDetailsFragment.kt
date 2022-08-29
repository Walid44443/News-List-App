package com.linkdevelopment.walid44443.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.linkdevelopment.walid44443.R
import com.linkdevelopment.walid44443.base.BaseFragment
import com.linkdevelopment.walid44443.databinding.FragmentArticleDetailsBinding
import com.linkdevelopment.walid44443.models.response.Article
import com.linkdevelopment.walid44443.ui.home.HomeFragment
import com.linkdevelopment.walid44443.ui.home.HomeViewModel
import com.weightwatchers.ww_exercise_01.base.createViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class ArticleDetailsFragment :
    BaseFragment<FragmentArticleDetailsBinding, ArticleDetailsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_article_details
    override val viewModelClass: Class<ArticleDetailsViewModel>
        get() = ArticleDetailsViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory =
        createViewModelFactory { ArticleDetailsViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.viewState.onEach { observeState(it) }.launchIn(lifecycleScope)



        if (arguments?.isEmpty?.not() == true) {
            val article: Article = arguments?.get(HomeFragment.ARTICLE_OBJECT) as Article
            lifecycleScope.launchWhenCreated {
                viewModel.intents.send(ArticleDetailsIntents.GoIdle(article))
            }
        } else
            activity?.onBackPressed()

        binding.openWebsiteBtn.setOnClickListener {
            lifecycleScope.launch {
                binding?.article?.url?.let {
                    viewModel.intents.send(ArticleDetailsIntents.OpenWebsite(it))
                }

            }
        }
    }


    override fun onResume() {
        super.onResume()
        observeState(viewModel.viewState.value)
    }

    private fun observeState(state: ArticleViewState) {
        if (isBindingInitialized()) {
            when (state) {
                is ArticleViewState.DisplayClick -> {
                    state.article?.let { _article ->
                        binding.article = state.article
                        context?.let { _context ->
                            Glide.with(_context)
                                .load(_article.urlToImage)
                                .fitCenter()
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .into(binding.articleImage)
                        }
                        binding.executePendingBindings()
                    }
                }

                is ArticleViewState.OpenArticle -> {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(state.openArticleLink))
                    startActivity(browserIntent)
                    lifecycleScope.launchWhenCreated {
                        state.article?.let {
                            viewModel.intents.send(ArticleDetailsIntents.GoIdle(it))
                        }
                    }
                }
            }
        }
    }
}