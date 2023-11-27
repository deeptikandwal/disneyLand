package com.disneyland.domain.repository

import androidx.paging.PagingData
import com.disneyland.Outcome
import com.disneyland.domain.model.DisneyActor
import com.disneyland.domain.model.DisneyListCharacter
import kotlinx.coroutines.flow.Flow

interface DisneyCharactersRepository {
    fun fetchDisneyCharacters(): Flow<PagingData<DisneyListCharacter>>
    fun fetchDisneyCharacterById(id: String): Flow<Outcome<DisneyActor>>
}