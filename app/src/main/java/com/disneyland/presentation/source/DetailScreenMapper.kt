package com.disneyland.presentation.source

import com.disneyland.AppConstants.DESCRIPTION
import com.disneyland.domain.model.DisneyActor
import com.disneyland.presentation.model.Actor
import javax.inject.Inject

class DetailScreenMapper @Inject constructor() {

    fun mapToDetailScreenData(disneyActor: DisneyActor): Actor {
        return Actor(
            disneyActor.name.uppercase(),
            DESCRIPTION.plus(" ").plus(disneyActor.description),
            disneyActor.majorAttraction,
            disneyActor.enemies,
            disneyActor.allies,
            disneyActor.image
        )
    }
}