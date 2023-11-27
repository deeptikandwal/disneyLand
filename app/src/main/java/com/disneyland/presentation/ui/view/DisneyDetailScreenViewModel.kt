package com.disneyland.presentation.ui.view

import androidx.lifecycle.viewModelScope
import com.disneyland.Outcome
import com.disneyland.domain.usecase.DisneyActorUsecase
import com.disneyland.presentation.base.BaseViewModel
import com.disneyland.presentation.base.SideEffect
import com.disneyland.presentation.base.ViewIntent
import com.disneyland.presentation.base.ViewState
import com.disneyland.presentation.source.DetailScreenMapper
import com.disneyland.presentation.ui.components.DisneyDetailScreenIntent
import com.disneyland.presentation.ui.components.DisneyDetailScreenSideEffect
import com.disneyland.presentation.ui.components.DisneyDetailScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyDetailScreenViewModel @Inject constructor(
    private val disneyActorUsecase: DisneyActorUsecase,
    private val detailScreenMapper: DetailScreenMapper,
) : BaseViewModel<DisneyDetailScreenViewState, DisneyDetailScreenIntent, DisneyDetailScreenSideEffect>() {
    override val initialViewState: ViewState = DisneyDetailScreenViewState.LOADING
    override fun sendIntent(intent: ViewIntent) {
        when (intent) {
            is DisneyDetailScreenIntent.FetchCharacterById -> {
                fetchDisneyCharactersById(intent.id)
            }
        }
    }

    override fun navigate(sideEffect: SideEffect) {
        TODO("Not yet implemented")
    }

    private fun fetchDisneyCharactersById(id: String) {
        viewModelScope.launch {
            disneyActorUsecase(id)
                .collectLatest { outcome ->
                    when (outcome) {
                        is Outcome.Success -> {
                            val actor = detailScreenMapper.mapToDetailScreenData(outcome.value)
                            updateViewState(DisneyDetailScreenViewState.SUCCESS(actor))
                        }

                        is Outcome.Failure -> {
                            updateViewState(DisneyDetailScreenViewState.ERROR(outcome.error.message.toString()))
                        }
                    }
                }
        }
    }

}