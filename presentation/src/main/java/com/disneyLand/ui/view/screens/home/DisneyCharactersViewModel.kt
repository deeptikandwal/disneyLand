package com.disneyLand.ui.view.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disneyLand.Outcome
import com.disneyLand.base.SideEffect
import com.disneyLand.source.HomeScreenMapper
import com.disneyLand.usecase.DisneyCharactersListUsecaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyCharactersViewModel @Inject constructor(
    private val disneyCharactersListUsecase: DisneyCharactersListUsecaseImpl,
    private val homeScreenMapper: HomeScreenMapper,
) : ViewModel(), DisneyListMviContract {
    private var _viewState = MutableStateFlow(createInitialState())
    private var _sideEffect = MutableSharedFlow<DisneyListMviContract.DisneyListScreenSideEffect>()

    override fun createInitialState(): DisneyListMviContract.DisneyListScreenViewState =
        DisneyListMviContract.DisneyListScreenViewState.Loading

    override val viewState: StateFlow<DisneyListMviContract.DisneyListScreenViewState>
        get() = _viewState.asStateFlow()

    override val sideEffect: SharedFlow<DisneyListMviContract.DisneyListScreenSideEffect>
        get() = _sideEffect.asSharedFlow()

    private fun fetchDisneyCharacters() {
        viewModelScope.launch {
            disneyCharactersListUsecase().collectLatest { outcome ->
                when (outcome) {
                    is Outcome.Success -> {
                        _viewState.value = DisneyListMviContract.DisneyListScreenViewState.Success(
                            homeScreenMapper.mapToHomeScreenData(outcome.value)
                        )
                    }

                    is Outcome.Failure -> {
                        _viewState.value =
                            DisneyListMviContract.DisneyListScreenViewState.Error(outcome.error.message.toString())

                    }
                }
            }

        }

    }

    private fun navigate(sideEffect: SideEffect) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect as DisneyListMviContract.DisneyListScreenSideEffect)
        }
    }

    override fun sendIntent(intent: DisneyListMviContract.DisneyListScreenIntent) {
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
