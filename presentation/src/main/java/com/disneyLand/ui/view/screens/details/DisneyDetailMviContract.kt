package com.disneyLand.ui.view.screens.details

import com.disneyLand.base.BaseMviContract
import com.disneyLand.base.SideEffect
import com.disneyLand.base.ViewIntent
import com.disneyLand.base.ViewState
import com.disneyLand.model.Actor

interface DisneyDetailMviContract :
    BaseMviContract<DisneyDetailMviContract.DisneyDetailScreenViewState, DisneyDetailMviContract.DisneyDetailScreenIntent, DisneyDetailMviContract.DisneyDetailScreenSideEffect> {
    sealed class DisneyDetailScreenViewState : ViewState {
        object Loading : DisneyDetailScreenViewState()
        data class Success(val data: Actor) : DisneyDetailScreenViewState()
        data class Error(val error: String) : DisneyDetailScreenViewState()
    }

    sealed class DisneyDetailScreenIntent : ViewIntent {
        class FetchCharacterById(val id: String) : DisneyDetailScreenIntent()
        object NavigateUp : DisneyDetailScreenIntent()
    }

    sealed class DisneyDetailScreenSideEffect : SideEffect {
        object NavigateUp : DisneyDetailScreenSideEffect()
    }
}
