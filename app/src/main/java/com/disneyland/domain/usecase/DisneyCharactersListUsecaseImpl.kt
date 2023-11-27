package com.disneyland.domain.usecase

import androidx.paging.PagingData
import com.disneyland.domain.model.DisneyListCharacter
import com.disneyland.domain.repository.DisneyCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisneyCharactersListUsecaseImpl @Inject constructor(private val disneyCharactersRepository: DisneyCharactersRepository) :
    DisneyCharactersListUsecase {
    override fun invoke(): Flow<PagingData<DisneyListCharacter>> =
        disneyCharactersRepository.fetchDisneyCharacters()
    }