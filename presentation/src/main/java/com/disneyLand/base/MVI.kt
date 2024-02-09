package com.disneyLand.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MVI<ViewState, ViewIntent, SideEffect> {

    val viewState: StateFlow<ViewState>

    val sideEffect: SharedFlow<SideEffect>
    fun sendIntent(intent: ViewIntent)

    fun updateViewState(viewState: ViewState)
    fun CoroutineScope.emitSideEffect(effect: SideEffect)
}
