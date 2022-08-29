package com.linkdevelopment.walid44443.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkdevelopment.walid44443.models.response.Article
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseViewModel<Intent, State>(
    val intents: Channel<Intent>
) : ViewModel() {

    init {
        this.viewModelScope.launch {
            this@BaseViewModel.handleIntents()
        }
    }

    protected abstract suspend fun handleIntents()
    protected abstract fun viewStateReducer(previousState: State, newState: State): State

    override fun onCleared() {
        this.intents.cancel()
        super.onCleared()
    }
}