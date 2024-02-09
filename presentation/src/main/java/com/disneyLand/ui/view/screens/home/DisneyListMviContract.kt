package com.disneyLand.ui.view.screens.home

import com.disneyLand.base.MVI
import com.disneyLand.base.SideEffect
import com.disneyLand.base.ViewIntent
import com.disneyLand.base.ViewState
import com.disneyLand.model.Character

interface DisneyListMviContract : MVI<ViewState, ViewIntent, SideEffect> {
    sealed interface DisneyListScreenViewState : ViewState {
        object Loading : DisneyListScreenViewState
        data class Success(val data: List<Character>) : DisneyListScreenViewState
        data class Error(val error: String) : DisneyListScreenViewState
    }

    sealed interface DisneyListScreenIntent : ViewIntent {
        object FetchCharactersList : DisneyListScreenIntent
        data class NavigateToDetails(val id: Int) : DisneyListScreenIntent
        object NavigateUp : DisneyListScreenIntent
    }

    sealed interface DisneyListScreenSideEffect : SideEffect {
        data class NavigateToDetailsScreen(val id: Int) : DisneyListScreenSideEffect
        object NavigateUp : DisneyListScreenSideEffect
    }
}
