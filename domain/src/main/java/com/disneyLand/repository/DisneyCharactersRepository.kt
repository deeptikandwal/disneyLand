package com.disneyLand.repository

import com.disneyLand.Outcome
import com.disneyLand.model.DisneyActor
import com.disneyLand.model.DisneyListCharacter
import kotlinx.coroutines.flow.Flow

interface DisneyCharactersRepository {
    fun fetchDisneyCharacters(): Flow<Outcome<List<DisneyListCharacter>>>
    fun fetchDisneyCharacterById(id: String): Flow<Outcome<DisneyActor>>
}
