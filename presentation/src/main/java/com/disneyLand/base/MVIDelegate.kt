package com.disneyLand.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MVIDelegate constructor(initialState: ViewState) :
    MVI<ViewState, ViewIntent, SideEffect> {
    private val _viewState = MutableStateFlow(initialState)
    override val viewState = _viewState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    override val sideEffect = _sideEffect.asSharedFlow()
    override fun CoroutineScope.emitSideEffect(effect: SideEffect) {
        this.launch {
            _sideEffect.emit(effect)
        }
    }

    override fun updateViewState(viewState: ViewState) {
        _viewState.value = viewState
    }

    override fun sendIntent(intent: ViewIntent) {
        //
    }
}

fun <ViewState, ViewIntent, SideEffect> mvi(
    initialUiState: com.disneyLand.base.ViewState
): MVIDelegate = MVIDelegate(initialUiState)
