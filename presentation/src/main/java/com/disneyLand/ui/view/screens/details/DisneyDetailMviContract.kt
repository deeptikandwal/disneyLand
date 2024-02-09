package com.disneyLand.ui.view.screens.details

import com.disneyLand.base.MVI
import com.disneyLand.base.SideEffect
import com.disneyLand.base.ViewIntent
import com.disneyLand.base.ViewState
import com.disneyLand.model.Actor

interface DisneyDetailMviContract : MVI<ViewState, ViewIntent, SideEffect> {
    sealed interface DisneyDetailScreenViewState : ViewState {
        object Loading : DisneyDetailScreenViewState
        data class Success(val data: Actor) : DisneyDetailScreenViewState
        data class Error(val error: String) : DisneyDetailScreenViewState
    }

    sealed interface DisneyDetailScreenIntent : ViewIntent {
        class FetchCharacterById(val id: String) : DisneyDetailScreenIntent
        object NavigateUp : DisneyDetailScreenIntent
    }

    sealed interface DisneyDetailScreenSideEffect : SideEffect {
        object NavigateUp : DisneyDetailScreenSideEffect
    }
}
