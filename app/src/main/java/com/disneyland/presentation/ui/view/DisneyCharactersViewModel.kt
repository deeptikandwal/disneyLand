package com.disneyland.presentation.ui.view

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.cachedIn
import com.disneyland.domain.usecase.DisneyCharactersListUsecase
import com.disneyland.presentation.base.BaseViewModel
import com.disneyland.presentation.base.SideEffect
import com.disneyland.presentation.base.ViewIntent
import com.disneyland.presentation.base.ViewState
import com.disneyland.presentation.source.HomeScreenMapper
import com.disneyland.presentation.ui.components.DisneyListScreenIntent
import com.disneyland.presentation.ui.components.DisneyListScreenSideEffect
import com.disneyland.presentation.ui.components.DisneyListScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyCharactersViewModel @Inject constructor(
    private val disneyCharactersListUsecase: DisneyCharactersListUsecase,
    private val homeScreenMapper: HomeScreenMapper,
) : BaseViewModel<DisneyListScreenViewState, DisneyListScreenIntent, DisneyListScreenSideEffect>() {
    override val initialViewState: ViewState = DisneyListScreenViewState.LOADING

    private fun fetchDisneyCharacters() {
        val flow = disneyCharactersListUsecase().map { pagingData ->
            homeScreenMapper.mapToHomeScreenData(pagingData)
        }.cachedIn(viewModelScope)
        updateViewState(DisneyListScreenViewState.SUCCESS(flow))
    }

    fun handleLoadState(loadStates: LoadStates) {
        val errorLoadState = arrayOf(
            loadStates.append,
            loadStates.prepend,
            loadStates.refresh
        ).filterIsInstance(LoadState.Error::class.java).firstOrNull()
        val throwable = errorLoadState?.error
        if (throwable?.localizedMessage?.isNotEmpty() == true) {
            updateViewState(DisneyListScreenViewState.ERROR(throwable.localizedMessage!!))
        }
    }

    override fun sendIntent(intent: ViewIntent) {
        when (intent) {
            is DisneyListScreenIntent.FetchCharactersList -> {
                fetchDisneyCharacters()
            }

            is DisneyListScreenIntent.NavigateToDetails -> {
                navigate(DisneyListScreenSideEffect.NavigateToDetailsScreen(intent.id))
            }

            is DisneyListScreenIntent.NavigateUp -> {
                navigate(DisneyListScreenSideEffect.NavigateUp)
            }

            else -> {
                //no implentation
            }
        }
    }

    override fun navigate(sideEffect: SideEffect) {
        viewModelScope.launch {
            updateSideEffect(sideEffect as DisneyListScreenSideEffect)
        }
    }

}