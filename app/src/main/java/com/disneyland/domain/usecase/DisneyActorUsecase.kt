package com.disneyland.domain.usecase

import com.disneyland.Outcome
import com.disneyland.domain.entity.DisneyActor
import kotlinx.coroutines.flow.Flow

interface DisneyActorUsecase {
    operator fun invoke(id: String): Flow<Outcome<DisneyActor>>
}