package com.disneyLand.usecase

import androidx.paging.PagingData
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.repository.DisneyCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisneyCharactersListUsecaseImpl @Inject constructor(private val disneyCharactersRepository: DisneyCharactersRepository) :
    DisneyCharactersListUsecase {
    override fun invoke(): Flow<PagingData<DisneyListCharacter>> =
        disneyCharactersRepository.fetchDisneyCharacters()
    }