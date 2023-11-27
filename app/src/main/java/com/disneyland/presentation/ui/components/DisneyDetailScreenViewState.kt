package com.disneyland.presentation.ui.components

import com.disneyland.presentation.base.SideEffect
import com.disneyland.presentation.base.ViewIntent
import com.disneyland.presentation.base.ViewState
import com.disneyland.presentation.model.Actor

sealed class DisneyDetailScreenViewState : ViewState {
    object LOADING : DisneyDetailScreenViewState()
    data class SUCCESS(val data: Actor) : DisneyDetailScreenViewState()
    data class ERROR(val error: String) : DisneyDetailScreenViewState()
}

sealed class DisneyDetailScreenIntent : ViewIntent {
    class FetchCharacterById(val id: String) : DisneyDetailScreenIntent()
    object NavigateUp : DisneyDetailScreenIntent()
}

sealed class DisneyDetailScreenSideEffect : SideEffect {
    object NavigateUp : DisneyDetailScreenSideEffect()
}