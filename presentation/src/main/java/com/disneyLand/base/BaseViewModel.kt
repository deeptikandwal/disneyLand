package com.disneyLand.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<vs : ViewState, vi : ViewIntent, se : SideEffect> : ViewModel() {
    abstract val initialViewState: ViewState
    private var lastViewState: ViewState = initialViewState

    private var _viewState = MutableStateFlow(initialViewState)
    val viewState = _viewState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun updateViewState(state: ViewState) {
        _viewState.value = state
        lastViewState = state
    }

    fun updateSideEffect(sideEffect: SideEffect) {
        viewModelScope.launch {
            _sideEffect.emit( sideEffect)
        }
    }

    abstract fun sendIntent(intent: ViewIntent)
    abstract fun navigate(sideEffect: SideEffect)
}