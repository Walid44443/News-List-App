package com.linkdevelopment.walid44443.ui.home

import com.linkdevelopment.walid44443.base.ViewState
import com.linkdevelopment.walid44443.models.response.Article


sealed class HomeViewState(
    open var isLoading: Boolean = false,
    open var clickedArticle: Article? = null,
    open var articleList: List<Article>? = null,
    open var errorMsg: String? = "",

    ) : ViewState {


    object Idle : HomeViewState(clickedArticle = null)

    object Loading :
        HomeViewState(isLoading = true)

    data class SuccessGetArticlesList(override var articleList: List<Article>?) :
        HomeViewState(articleList = articleList)


    object EmptyArticlesList :
        HomeViewState(articleList = emptyList())


    data class ArticleClick(override var clickedArticle: Article?) :
        HomeViewState(clickedArticle = clickedArticle)

    data class Error(override var errorMsg: String?) : HomeViewState(errorMsg = errorMsg)


}