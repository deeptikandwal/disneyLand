package com.disneyLand.ui.view

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.disneyLand.base.BaseViewModel
import com.disneyLand.base.SideEffect
import com.disneyLand.base.ViewIntent
import com.disneyLand.base.ViewState
import com.disneyLand.model.Character
import com.disneyLand.source.HomeScreenMapper
import com.disneyLand.ui.components.DisneyListScreenIntent
import com.disneyLand.ui.components.DisneyListScreenSideEffect
import com.disneyLand.ui.components.DisneyListScreenViewState
import com.disneyLand.usecase.DisneyCharactersListUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyCharactersViewModel @Inject constructor(
    private val disneyCharactersListUsecase: DisneyCharactersListUsecase,
    private val homeScreenMapper: HomeScreenMapper,
) : BaseViewModel<DisneyListScreenViewState, DisneyListScreenIntent, DisneyListScreenSideEffect>() {
    private lateinit var flow: Flow<PagingData<Character>>
    override val initialViewState: ViewState = DisneyListScreenViewState.LOADING

    private fun fetchDisneyCharacters() {
        flow = disneyCharactersListUsecase().map { pagingData ->
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
                // no implentation
            }
        }
    }

    override fun navigate(sideEffect: SideEffect) {
        viewModelScope.launch {
            updateSideEffect(sideEffect as DisneyListScreenSideEffect)
        }
    }
}
