package com.linkdevelopment.walid44443.ui.home

import android.view.Menu
import android.view.MenuItem
import com.linkdevelopment.walid44443.models.response.Article

sealed class HomeIntents {
    object GoIdle : HomeIntents()
    object GetData : HomeIntents()
    data class OnArticleClick(val article: Article) : HomeIntents()
}