package com.linkdevelopment.walid44443.ui.activity

import com.linkdevelopment.walid44443.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow

class MainActivityViewModel(
    intents: Channel<MainActivityIntents> = Channel(Channel.CONFLATED),
    state: MutableStateFlow<MainActivityState> = MutableStateFlow(MainActivityState.Idle)
) : BaseViewModel<MainActivityIntents, MainActivityState>(intents) {

    private val _viewState: MutableStateFlow<MainActivityState> = state
    val viewState: StateFlow<MainActivityState>
        get() = this._viewState

    override suspend fun handleIntents() {
        this.intents.consumeAsFlow().collect {
            when (it) {
                is MainActivityIntents.GoIdle -> {
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, MainActivityState.Idle)
                }

                is MainActivityIntents.ClickOnDrawerItem -> {
                    this._viewState.value =
                        this.viewStateReducer(
                            this._viewState.value,
                            MainActivityState.DrawerItemClicked(it.menu)
                        )
                }
            }
        }
    }


    override fun viewStateReducer(
        previousState: MainActivityState,
        newState: MainActivityState
    ): MainActivityState {
        return when (newState) {
            is MainActivityState.Idle,
            is MainActivityState.DrawerItemClicked -> {
                newState
            }
        }
    }

}