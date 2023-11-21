package com.disneyland.domain.repository

import androidx.paging.PagingData
import com.disneyland.domain.entity.DisneyActor
import com.disneyland.domain.entity.DisneyCharacter
import kotlinx.coroutines.flow.Flow

interface DisneyCharactersRepository {
    fun fetchDisneyCharacters(): Flow<PagingData<DisneyCharacter>>
    fun fetchDisneyCharacterById(id: String): Flow<DisneyActor>
}