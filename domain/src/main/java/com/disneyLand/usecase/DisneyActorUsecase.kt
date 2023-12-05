package com.disneyLand.usecase

import com.disneyLand.Outcome
import com.disneyLand.model.DisneyActor
import kotlinx.coroutines.flow.Flow

interface DisneyActorUsecase {
    operator fun invoke(id: String): Flow<Outcome<DisneyActor>>
}
