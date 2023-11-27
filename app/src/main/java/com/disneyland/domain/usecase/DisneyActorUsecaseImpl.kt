package com.disneyland.domain.usecase

import com.disneyland.Outcome
import com.disneyland.domain.model.DisneyActor
import com.disneyland.domain.repository.DisneyCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisneyActorUsecaseImpl @Inject constructor(val disneyCharactersRepository: DisneyCharactersRepository) :
    DisneyActorUsecase {
    override fun invoke(id: String): Flow<Outcome<DisneyActor>> =
        disneyCharactersRepository.fetchDisneyCharacterById(id)

}