package com.disneyLand.ui.view.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disneyLand.Outcome
import com.disneyLand.base.SideEffect
import com.disneyLand.source.mapToDetailScreenData
import com.disneyLand.usecase.DisneyActorUsecaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyDetailScreenViewModel @Inject constructor(
    private val disneyActorUsecase: DisneyActorUsecaseImpl,
) : DisneyDetailMviContract, ViewModel() {
    private var _viewState = MutableStateFlow(createInitialState())
    private var _sideEffect =
        MutableSharedFlow<DisneyDetailMviContract.DisneyDetailScreenSideEffect>()

    override fun createInitialState(): DisneyDetailMviContract.DisneyDetailScreenViewState =
        DisneyDetailMviContract.DisneyDetailScreenViewState.Loading

    override val viewState: StateFlow<DisneyDetailMviContract.DisneyDetailScreenViewState>
        get() = _viewState.asStateFlow()
    override val sideEffect: SharedFlow<DisneyDetailMviContract.DisneyDetailScreenSideEffect>
        get() = _sideEffect.asSharedFlow()

    override fun sendIntent(intent: DisneyDetailMviContract.DisneyDetailScreenIntent) {
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
            _sideEffect.emit(sideEffect as DisneyDetailMviContract.DisneyDetailScreenSideEffect.NavigateUp)
        }
    }

    private fun fetchDisneyCharactersById(id: String) {
        _viewState.value = DisneyDetailMviContract.DisneyDetailScreenViewState.Loading

        viewModelScope.launch {
            disneyActorUsecase(id)
                .catch {
                    _viewState.value =
                        DisneyDetailMviContract.DisneyDetailScreenViewState.Error(it.message.toString())
                }
                .collectLatest { outcome ->
                    when (outcome) {
                        is Outcome.Success -> {
                            _viewState.value =
                                DisneyDetailMviContract.DisneyDetailScreenViewState.Success((outcome.value).mapToDetailScreenData())
                        }

                        is Outcome.Failure -> {
                            _viewState.value =
                                DisneyDetailMviContract.DisneyDetailScreenViewState.Error(outcome.error.message.toString())
                        }
                    }
                }
        }
    }

    fun getActorCharacteristicsList(str: String): ArrayList<String> {
        val list = arrayListOf<String>()
        if (str.isNotEmpty()) {
            val items = str.split(":")
            items.forEach {
                list.add(it)
            }
        }
        return list
    }

    fun setVisibility(str: String): Boolean {
        if (str.isNotBlank()) {
            return true
        }
        return false
    }
}
