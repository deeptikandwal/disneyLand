package com.disneyLand.source

import com.disneyLand.model.Actor
import com.disneyLand.model.DisneyActor
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

    private companion object {
        const val DESCRIPTION =
            "This character featured in films, tv shows and video games like :"
    }
}
