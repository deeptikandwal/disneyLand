package com.disneyLand.repository

import com.disneyLand.Result
import com.disneyLand.model.DisneyActor
import com.disneyLand.model.DisneyListCharacter

interface DisneyCharactersRepository {
    suspend fun fetchDisneyCharacters(): Result<List<DisneyListCharacter>>
    suspend fun fetchDisneyCharacterById(id: String): Result<DisneyActor>
}
