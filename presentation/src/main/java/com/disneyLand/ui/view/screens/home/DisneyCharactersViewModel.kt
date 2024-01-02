package com.disneyLand.ui.view.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.disneyLand.base.SideEffect
import com.disneyLand.model.Character
import com.disneyLand.source.HomeScreenMapper
import com.disneyLand.usecase.DisneyCharactersListUsecaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyCharactersViewModel @Inject constructor(
    private val disneyCharactersListUsecase: DisneyCharactersListUsecaseImpl,
    private val homeScreenMapper: HomeScreenMapper
) : ViewModel(), DisneyListMviContract {
    private lateinit var flow: Flow<PagingData<Character>>
    private var _viewState = MutableStateFlow(createInitialState())
    private var _sideEffect = MutableSharedFlow<DisneyListMviContract.DisneyListScreenSideEffect>()

    override fun createInitialState(): DisneyListMviContract.DisneyListScreenViewState =
        DisneyListMviContract.DisneyListScreenViewState.Loading

    override val viewState: StateFlow<DisneyListMviContract.DisneyListScreenViewState>
        get() = _viewState.asStateFlow()

    override val sideEffect: SharedFlow<DisneyListMviContract.DisneyListScreenSideEffect>
        get() = _sideEffect.asSharedFlow()

    private fun fetchDisneyCharacters() {
        flow = disneyCharactersListUsecase().map { pagingData ->
            homeScreenMapper.mapToHomeScreenData(pagingData)
        }.cachedIn(viewModelScope)
        _viewState.value = DisneyListMviContract.DisneyListScreenViewState.Success(flow)
    }

    fun handleLoadState(loadStates: LoadStates) {
        val errorLoadState = arrayOf(
            loadStates.append,
            loadStates.prepend,
            loadStates.refresh
        ).filterIsInstance(LoadState.Error::class.java).firstOrNull()
        val throwable = errorLoadState?.error
        if (throwable?.localizedMessage?.isNotEmpty() == true) {
            _viewState.value =
                DisneyListMviContract.DisneyListScreenViewState.Error(throwable.localizedMessage!!)
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
                navigate(DisneyListMviContract.DisneyListScreenSideEffect.NavigateToDetailsScreen(intent.id))
            }

            is DisneyListMviContract.DisneyListScreenIntent.NavigateUp -> {
                navigate(DisneyListMviContract.DisneyListScreenSideEffect.NavigateUp)
            }
        }
    }
}
