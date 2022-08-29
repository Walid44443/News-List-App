package com.linkdevelopment.walid44443.ui.details

import com.linkdevelopment.walid44443.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow

class ArticleDetailsViewModel(
    intents: Channel<ArticleDetailsIntents> = Channel(Channel.CONFLATED),
    state: MutableStateFlow<ArticleViewState> = MutableStateFlow(ArticleViewState.DisplayClick(null)),
) : BaseViewModel<ArticleDetailsIntents, ArticleViewState>(intents) {

    private val _viewState: MutableStateFlow<ArticleViewState> = state
    val viewState: StateFlow<ArticleViewState>
        get() = this._viewState

    override suspend fun handleIntents() {
        this.intents.consumeAsFlow().collect {
            when (it) {
                is ArticleDetailsIntents.GoIdle -> {
                    this._viewState.value =
                        this.viewStateReducer(
                            this._viewState.value,
                            ArticleViewState.DisplayClick(it.article)
                        )
                }

                is ArticleDetailsIntents.OpenWebsite -> {
                    this._viewState.value =
                        this.viewStateReducer(
                            this._viewState.value,
                            ArticleViewState.OpenArticle(it.articleLink)
                        )
                }
            }
        }
    }


    override fun viewStateReducer(
        previousState: ArticleViewState,
        newState: ArticleViewState
    ): ArticleViewState {
        return when (newState) {
            is ArticleViewState.OpenArticle-> {
                newState.apply {
                    this.article = previousState.article
                }
            }
            is ArticleViewState.DisplayClick -> {
                newState
            }
        }
    }
}