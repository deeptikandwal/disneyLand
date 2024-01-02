package com.disneyLand.source

import com.disneyLand.model.Actor
import com.disneyLand.model.DisneyActor

private const val DESCRIPTION =
    "This character featured in films, tv shows and video games like :"

fun DisneyActor.mapToDetailScreenData(): Actor {
    return Actor(
        name.uppercase(),
        DESCRIPTION.plus(" ").plus(description),
        majorAttraction,
        enemies,
        allies,
        image
    )
}