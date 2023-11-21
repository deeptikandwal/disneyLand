package com.disneyland.presentation.ui.components

import androidx.paging.PagingData
import com.disneyland.presentation.base.SideEffect
import com.disneyland.presentation.base.ViewIntent
import com.disneyland.presentation.base.ViewState
import com.disneyland.presentation.model.Character
import kotlinx.coroutines.flow.Flow

sealed class DisneyListScreenViewState : ViewState {
    object LOADING : DisneyListScreenViewState()
    data class SUCCESS(val data: Flow<PagingData<Character>>) : DisneyListScreenViewState()
    data class ERROR(val error: String) : DisneyListScreenViewState()
}

sealed class DisneyListScreenIntent : ViewIntent {
    object FetchCharactersList : DisneyListScreenIntent()
    class NavigateToDetails(val id: Int) : DisneyListScreenIntent()
    object NavigateUp : DisneyListScreenIntent()
}

sealed class DisneyListScreenSideEffect : SideEffect {
    class NavigateToDetailsScreen(val id: Int) : DisneyListScreenSideEffect()
    object NavigateUp: DisneyListScreenSideEffect()
}