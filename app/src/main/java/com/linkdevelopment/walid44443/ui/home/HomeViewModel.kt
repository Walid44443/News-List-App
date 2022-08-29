package com.linkdevelopment.walid44443.ui.home

import com.linkdevelopment.walid44443.api.ClientAPI
import com.linkdevelopment.walid44443.base.BaseViewModel
import com.linkdevelopment.walid44443.repository.ArticlesRepo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow

class HomeViewModel(
    clientAPI: ClientAPI = ClientAPI(),
    private var articlesRepo: ArticlesRepo = ArticlesRepo(clientAPI),
    intents: Channel<HomeIntents> = Channel(Channel.CONFLATED),
    state: MutableStateFlow<HomeViewState> = MutableStateFlow(HomeViewState.Idle),
) : BaseViewModel<HomeIntents, HomeViewState>(intents) {

    private val _viewState: MutableStateFlow<HomeViewState> = state
    val viewState: StateFlow<HomeViewState>
        get() = this._viewState

    override suspend fun handleIntents() {
        this.intents.consumeAsFlow().collect {
            when (it) {
                is HomeIntents.GoIdle -> {
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, HomeViewState.Idle)
                }

                is HomeIntents.OnArticleClick -> {
                    if (this._viewState.value.clickedArticle?.title.equals(it.article.title).not())
                        this._viewState.value =
                            this.viewStateReducer(
                                this._viewState.value,
                                HomeViewState.ArticleClick(clickedArticle = it.article)
                            )
                }

                is HomeIntents.GetData -> {
                    this._viewState.value =
                        this.viewStateReducer(
                            this._viewState.value,
                            HomeViewState.Loading
                        )

                    this._viewState.value =
                        this.viewStateReducer(
                            this._viewState.value,
                            getArticleList()
                        )
                }
            }
        }
    }


    override fun viewStateReducer(
        previousState: HomeViewState,
        newState: HomeViewState
    ): HomeViewState {
        return when (newState) {
            is HomeViewState.Idle,
            is HomeViewState.Error,
            is HomeViewState.EmptyArticlesList,
            is HomeViewState.ArticleClick,
            is HomeViewState.Loading -> {
                newState.apply {
                    this.articleList = previousState.articleList
                }
            }
            is HomeViewState.SuccessGetArticlesList -> {
                newState
            }
        }
    }


    private suspend fun getArticleList(): HomeViewState {
        var response = articlesRepo.getItemList()
        if (response.error.isNullOrBlank().not())
            return HomeViewState.Error(response.error)
        else if (response.articles?.isEmpty() == true)
            return HomeViewState.EmptyArticlesList
        else
            return HomeViewState.SuccessGetArticlesList(response.articles)
    }

}