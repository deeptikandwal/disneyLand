package com.disneyLand.usecase

import com.disneyLand.Result
import com.disneyLand.model.DisneyActor
import com.disneyLand.repository.DisneyCharactersRepository
import javax.inject.Inject

class DisneyActorUsecase @Inject constructor(private val disneyCharactersRepository: DisneyCharactersRepository) {
    suspend operator fun invoke(id: String): Result<DisneyActor> =
        disneyCharactersRepository.fetchDisneyCharacterById(id)
}
