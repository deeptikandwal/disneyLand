package com.disneyLand.ui.view.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disneyLand.Result
import com.disneyLand.base.MVI
import com.disneyLand.base.SideEffect
import com.disneyLand.base.ViewIntent
import com.disneyLand.base.ViewState
import com.disneyLand.base.mvi
import com.disneyLand.source.DetailScreenMapper
import com.disneyLand.usecase.DisneyActorUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyDetailScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val disneyActorUsecase: DisneyActorUsecase,
    private val detailScreenMapper: DetailScreenMapper
) : MVI<ViewState, ViewIntent, SideEffect> by mvi<DisneyDetailMviContract.DisneyDetailScreenViewState,
    DisneyDetailMviContract.DisneyDetailScreenIntent,
    DisneyDetailMviContract.DisneyDetailScreenSideEffect>(DisneyDetailMviContract.DisneyDetailScreenViewState.Loading),
    ViewModel() {

    init {
        val id = savedStateHandle.get<String>(ID).toString()
        sendIntent(DisneyDetailMviContract.DisneyDetailScreenIntent.FetchCharacterById(id))
    }

    override fun sendIntent(intent: ViewIntent) {
        when (intent) {
            is DisneyDetailMviContract.DisneyDetailScreenIntent.FetchCharacterById -> {
                fetchDisneyCharactersById(intent.id)
            }

            is DisneyDetailMviContract.DisneyDetailScreenIntent.NavigateUp -> navigate(
                DisneyDetailMviContract.DisneyDetailScreenSideEffect.NavigateUp
            )
        }
    }

    private fun navigate(sideEffect: SideEffect) {
        viewModelScope.launch {
            emitSideEffect(sideEffect as DisneyDetailMviContract.DisneyDetailScreenSideEffect.NavigateUp)
        }
    }

    private fun fetchDisneyCharactersById(id: String) {
        viewModelScope.launch {
            when (val result = disneyActorUsecase(id)) {
                is Result.Success -> {
                    updateViewState(
                        DisneyDetailMviContract.DisneyDetailScreenViewState.Success(
                            detailScreenMapper.mapToDetailScreenData(result.value)
                        )
                    )
                }

                is Result.Error -> {
                    updateViewState(
                        DisneyDetailMviContract.DisneyDetailScreenViewState.Error(result.error.message.toString())
                    )
                }
            }
        }
    }

    fun getActorCharacteristicsList(str: String): ArrayList<String> {
        val list = arrayListOf<String>()
        if (str.isNotEmpty()) {
            val items = str.split(":")
            items.forEach {
                val text = it.replace("null", "")
                list.add(text)
            }
        }
        return list
    }

    private companion object {
        const val ID = "id"
    }
}
