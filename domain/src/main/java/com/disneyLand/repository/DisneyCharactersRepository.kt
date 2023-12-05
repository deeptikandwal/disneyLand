package com.disneyLand.repository

import androidx.paging.PagingData
import com.disneyLand.Outcome
import com.disneyLand.model.DisneyActor
import com.disneyLand.model.DisneyListCharacter
import kotlinx.coroutines.flow.Flow

interface DisneyCharactersRepository {
    fun fetchDisneyCharacters(): Flow<PagingData<DisneyListCharacter>>
    fun fetchDisneyCharacterById(id: String): Flow<Outcome<DisneyActor>>
}
