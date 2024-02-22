package com.disneyLand.ui.view.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disneyLand.Result
import com.disneyLand.base.MVI
import com.disneyLand.base.SideEffect
import com.disneyLand.base.ViewIntent
import com.disneyLand.base.ViewState
import com.disneyLand.base.mvi
import com.disneyLand.source.HomeScreenMapper
import com.disneyLand.usecase.DisneyCharactersListUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyCharactersViewModel @Inject constructor(
    private val disneyCharactersListUsecase: DisneyCharactersListUsecase,
    private val homeScreenMapper: HomeScreenMapper
) : ViewModel(),
    MVI<ViewState, ViewIntent, SideEffect> by mvi<DisneyListMviContract.DisneyListScreenViewState,
        DisneyListMviContract.DisneyListScreenIntent,
        DisneyListMviContract.DisneyListScreenSideEffect>(DisneyListMviContract.DisneyListScreenViewState.Loading) {

    init {
        sendIntent(DisneyListMviContract.DisneyListScreenIntent.FetchCharactersList)
    }
    private fun fetchDisneyCharacters() {
        viewModelScope.launch {
            when (val result = disneyCharactersListUsecase()) {
                is Result.Success -> {
                    val list = homeScreenMapper.mapToHomeScreenData(result.value)
                    updateViewState(DisneyListMviContract.DisneyListScreenViewState.Success(list))
                }

                is Result.Error -> {
                    updateViewState(
                        DisneyListMviContract.DisneyListScreenViewState.Error(result.error.message.toString())
                    )
                }
            }
        }
    }

    private fun navigate(sideEffect: SideEffect) {
        viewModelScope.emitSideEffect(sideEffect as DisneyListMviContract.DisneyListScreenSideEffect)
    }

    override fun sendIntent(intent: ViewIntent) {
        when (intent) {
            is DisneyListMviContract.DisneyListScreenIntent.FetchCharactersList -> {
                fetchDisneyCharacters()
            }

            is DisneyListMviContract.DisneyListScreenIntent.NavigateToDetails -> {
                navigate(
                    DisneyListMviContract.DisneyListScreenSideEffect.NavigateToDetailsScreen(
                        intent.id
                    )
                )
            }

            is DisneyListMviContract.DisneyListScreenIntent.NavigateUp -> {
                navigate(DisneyListMviContract.DisneyListScreenSideEffect.NavigateUp)
            }
        }
    }
}
