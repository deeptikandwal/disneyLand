package com.disneyLand.usecase

import com.disneyLand.Outcome
import com.disneyLand.model.DisneyActor
import com.disneyLand.repository.DisneyCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisneyActorUsecaseImpl @Inject constructor(private val disneyCharactersRepository: DisneyCharactersRepository) {
    operator fun invoke(id: String): Flow<Outcome<DisneyActor>> =
        disneyCharactersRepository.fetchDisneyCharacterById(id)
}
