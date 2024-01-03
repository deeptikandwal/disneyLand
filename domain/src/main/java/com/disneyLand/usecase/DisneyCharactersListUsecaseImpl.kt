package com.disneyLand.usecase

import com.disneyLand.Outcome
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.repository.DisneyCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisneyCharactersListUsecaseImpl @Inject constructor(private val disneyCharactersRepository: DisneyCharactersRepository) {
    operator fun invoke(): Flow<Outcome<List<DisneyListCharacter>>> =
        disneyCharactersRepository.fetchDisneyCharacters()
}
