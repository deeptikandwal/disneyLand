package com.disneyLand.usecase

import com.disneyLand.Result
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.repository.DisneyCharactersRepository
import javax.inject.Inject

class DisneyCharactersListUsecase @Inject constructor(private val disneyCharactersRepository: DisneyCharactersRepository) {
    suspend operator fun invoke(): Result<List<DisneyListCharacter>> =
        disneyCharactersRepository.fetchDisneyCharacters()
}
