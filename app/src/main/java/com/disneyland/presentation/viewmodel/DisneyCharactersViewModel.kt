package com.disneyland.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.disneyland.domain.entity.DisneyCharacter
import com.disneyland.domain.usecase.DisneyActorUsecase
import com.disneyland.domain.usecase.DisneyCharactersListUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyCharactersViewModel @Inject constructor(
    private val disneyCharactersListUsecase: DisneyCharactersListUsecase,
    private val disneyActorUsecase: DisneyActorUsecase,
) :
    ViewModel() {

    fun fetchDisneyCharacters(): Flow<PagingData<DisneyCharacter>> =
        disneyCharactersListUsecase().cachedIn(viewModelScope)

    fun fetchDisneyCharactersById(id: String) {
        viewModelScope.launch {
            disneyActorUsecase(id).collectLatest {
            }
        }
    }


}