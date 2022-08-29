package com.linkdevelopment.walid44443.ui.details

import com.linkdevelopment.walid44443.base.ViewState
import com.linkdevelopment.walid44443.models.response.Article


sealed class ArticleViewState(
    open var openArticleLink: String = "",
    open var article: Article? = null,
) : ViewState {

    data class DisplayClick(override var article: Article?) :
        ArticleViewState(article = article)

    data class OpenArticle(override var openArticleLink: String) :
        ArticleViewState(openArticleLink = openArticleLink)

}