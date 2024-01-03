package com.disneyLand.ui.view.screens.home

import com.disneyLand.base.BaseMviContract
import com.disneyLand.base.SideEffect
import com.disneyLand.base.ViewIntent
import com.disneyLand.base.ViewState
import com.disneyLand.model.Character

interface DisneyListMviContract :
    BaseMviContract<DisneyListMviContract.DisneyListScreenViewState, DisneyListMviContract.DisneyListScreenIntent, DisneyListMviContract.DisneyListScreenSideEffect> {
    sealed class DisneyListScreenViewState : ViewState {
        object Loading : DisneyListScreenViewState()
        data class Success(val data: List<Character>) : DisneyListScreenViewState()
        data class Error(val error: String) : DisneyListScreenViewState()
    }

    sealed class DisneyListScreenIntent : ViewIntent {
        object FetchCharactersList : DisneyListScreenIntent()
        class NavigateToDetails(val id: Int) : DisneyListScreenIntent()
        object NavigateUp : DisneyListScreenIntent()
    }

    sealed class DisneyListScreenSideEffect : SideEffect {
        class NavigateToDetailsScreen(val id: Int) : DisneyListScreenSideEffect()
        object NavigateUp : DisneyListScreenSideEffect()
    }
}
