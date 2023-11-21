package com.disneyland.domain.repository

import androidx.paging.PagingData
import com.disneyland.domain.entity.DisneyActor
import com.disneyland.domain.entity.DisneyListCharacter
import kotlinx.coroutines.flow.Flow

interface DisneyCharactersRepository {
    fun fetchDisneyCharacters(): Flow<PagingData<DisneyListCharacter>>
    fun fetchDisneyCharacterById(id: String): Flow<DisneyActor>
}