package com.disneyland.presentation.ui.view

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.disneyland.domain.usecase.DisneyActorUsecase
import com.disneyland.domain.usecase.DisneyCharactersListUsecase
import com.disneyland.presentation.base.BaseViewModel
import com.disneyland.presentation.base.SideEffect
import com.disneyland.presentation.base.ViewIntent
import com.disneyland.presentation.source.HomeScreenMapper
import com.disneyland.presentation.ui.components.DisneyListScreenIntent
import com.disneyland.presentation.ui.components.DisneyListScreenSideEffect
import com.disneyland.presentation.ui.components.DisneyListScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyCharactersViewModel @Inject constructor(
    private val disneyCharactersListUsecase: DisneyCharactersListUsecase,
    private val disneyActorUsecase: DisneyActorUsecase,
    private val homeScreenMapper: HomeScreenMapper,
) : BaseViewModel<DisneyListScreenViewState, DisneyListScreenIntent, DisneyListScreenSideEffect>() {

    private var _viewState =
        MutableStateFlow<DisneyListScreenViewState>(DisneyListScreenViewState.LOADING)
    val viewState = _viewState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<DisneyListScreenSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private fun fetchDisneyCharacters() {
        val flow = disneyCharactersListUsecase().map { pagingData ->
            homeScreenMapper.mapToHomeScreenData(pagingData)
        }.cachedIn(viewModelScope)
        _viewState.value = DisneyListScreenViewState.SUCCESS(flow)
    }

    fun fetchDisneyCharactersById(id: String) {
        viewModelScope.launch {
            disneyActorUsecase(id).collectLatest {

            }
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
            _sideEffect.emit(sideEffect as DisneyListScreenSideEffect)
        }
    }

}