package com.disneyLand.base

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BaseMviContract<ViewState, ViewIntent, SideEffect> {

    fun sendIntent(vi: ViewIntent)
    fun createInitialState(): ViewState

    val viewState: StateFlow<ViewState>

    val sideEffect: SharedFlow<SideEffect>
}
