package com.disneyland.domain.usecase

import com.disneyland.domain.entity.DisneyActor
import com.disneyland.domain.repository.DisneyCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisneyActorUsecaseImpl @Inject constructor(val disneyCharactersRepository: DisneyCharactersRepository) :
    DisneyActorUsecase {
    override fun invoke(id: String): Flow<DisneyActor> =
        disneyCharactersRepository.fetchDisneyCharacterById(id)

}