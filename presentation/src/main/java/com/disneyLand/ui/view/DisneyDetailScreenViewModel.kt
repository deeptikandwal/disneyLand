package com.disneyLand.ui.view

import androidx.lifecycle.viewModelScope
import com.disneyLand.Outcome
import com.disneyLand.base.BaseViewModel
import com.disneyLand.base.SideEffect
import com.disneyLand.base.ViewIntent
import com.disneyLand.base.ViewState
import com.disneyLand.source.DetailScreenMapper
import com.disneyLand.ui.components.DisneyDetailScreenIntent
import com.disneyLand.ui.components.DisneyDetailScreenSideEffect
import com.disneyLand.ui.components.DisneyDetailScreenViewState
import com.disneyLand.usecase.DisneyActorUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyDetailScreenViewModel @Inject constructor(
    private val disneyActorUsecase: DisneyActorUsecase,
    private val detailScreenMapper: DetailScreenMapper
) : BaseViewModel<DisneyDetailScreenViewState, DisneyDetailScreenIntent, DisneyDetailScreenSideEffect>() {
    override val initialViewState: ViewState = DisneyDetailScreenViewState.LOADING
    override fun sendIntent(intent: ViewIntent) {
        when (intent) {
            is DisneyDetailScreenIntent.FetchCharacterById -> {
                fetchDisneyCharactersById(intent.id)
            }

            is DisneyDetailScreenIntent.NavigateUp -> navigate(DisneyDetailScreenSideEffect.NavigateUp)
        }
    }

    override fun navigate(sideEffect: SideEffect) {
        updateSideEffect(sideEffect as DisneyDetailScreenSideEffect.NavigateUp)
    }

    private fun fetchDisneyCharactersById(id: String) {
        updateViewState(DisneyDetailScreenViewState.LOADING)

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
