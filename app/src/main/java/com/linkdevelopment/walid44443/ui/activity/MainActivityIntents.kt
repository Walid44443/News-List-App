package com.linkdevelopment.walid44443.ui.activity

import android.view.Menu
import android.view.MenuItem

sealed class MainActivityIntents {
    object GoIdle : MainActivityIntents()
    data class ClickOnDrawerItem(val menu: MenuItem) : MainActivityIntents()
}