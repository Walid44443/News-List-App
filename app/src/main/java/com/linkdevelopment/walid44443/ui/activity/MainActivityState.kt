package com.linkdevelopment.walid44443.ui.activity

import android.view.Menu
import android.view.MenuItem
import com.linkdevelopment.walid44443.base.ViewState
import com.linkdevelopment.walid44443.models.response.Article

sealed class MainActivityState(
    open val menu: MenuItem? = null,
) : ViewState {
    object Idle : MainActivityState()
    data class DrawerItemClicked(override val menu: MenuItem? = null) : MainActivityState()
}
