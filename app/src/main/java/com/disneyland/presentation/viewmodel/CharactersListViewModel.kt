package com.disneyland.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.disneyland.domain.entity.DisneyCharacter
import com.disneyland.domain.usecase.DisneyCharactersListUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(private val disneyCharactersListUsecase: DisneyCharactersListUsecase) :
    ViewModel() {

    fun fetchDisneyCharacters(): Flow<PagingData<DisneyCharacter>> = disneyCharactersListUsecase().cachedIn(viewModelScope)


}