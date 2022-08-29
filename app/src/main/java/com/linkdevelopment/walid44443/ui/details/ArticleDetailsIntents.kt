package com.linkdevelopment.walid44443.ui.details

import android.view.Menu
import android.view.MenuItem
import com.linkdevelopment.walid44443.models.response.Article

sealed class ArticleDetailsIntents {
    data class GoIdle(val article: Article) : ArticleDetailsIntents()
    data class OpenWebsite(val articleLink: String) : ArticleDetailsIntents()
}