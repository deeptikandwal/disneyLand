package com.disneyLand.source

import com.disneyLand.model.Actor
import com.disneyLand.model.DisneyActor
import javax.inject.Inject

class DetailScreenMapperImpl @Inject constructor() : DetailScreenMapper {

    override fun mapToDetailScreenData(disneyActor: DisneyActor): Actor {
        return Actor(
            disneyActor.name.uppercase(),
            getDescription(disneyActor),
            disneyActor.image
        )
    }

    private fun getDescription(disneyActor: DisneyActor): String {
        return "$DESCRIPTION " + disneyActor.description + Attractions.plus(disneyActor.majorAttraction)
            .takeIf {
                disneyActor.majorAttraction.isNotEmpty()
            } + Enemies.plus(disneyActor.enemies).takeIf {
            disneyActor.enemies.isNotEmpty()
        } + Allies.plus(disneyActor.allies).takeIf {
            disneyActor.allies.isNotEmpty()
        }
    }

    private companion object {
        const val DESCRIPTION = "This character featured in films, tv shows and video games like :"
        const val Attractions = "\n\nAttraction - "
        const val Enemies = "\nEnemies - "
        const val Allies = "\nAllies - "
    }
}
