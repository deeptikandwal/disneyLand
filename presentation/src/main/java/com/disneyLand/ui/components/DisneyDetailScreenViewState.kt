package com.disneyLand.ui.components

import com.disneyLand.base.SideEffect
import com.disneyLand.base.ViewIntent
import com.disneyLand.base.ViewState
import com.disneyLand.model.Actor

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