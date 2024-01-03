package com.disneyLand.source

import com.disneyLand.dto.Actor
import com.disneyLand.dto.DisneyOriginalActor
import com.disneyLand.dto.Info
import com.disneyLand.model.DisneyActor

class FakeDisneyActor {
    companion object {
        val successfulDisneyActor = DisneyOriginalActor(
            info = Info(count = 1, totalPages = 1),
            data = Actor(
                id = 268,
                films = arrayListOf("The Happiest Millionaire"),
                shortFilms = arrayListOf("The Happiest Millionaire"),
                tvShows = arrayListOf("The Happiest Millionaire"),
                videoGames = arrayListOf("The Happiest Millionaire"),
                parkAttractions = arrayListOf("12 alligators"),
                allies = arrayListOf("Cordelia Biddle (wife), Tonny Biddle"),
                enemies = arrayListOf("Tonny Biddle"),
                sourceUrl = "https://disney.fandom.com/wiki/Anthony_Biddle",
                name = "Anthony Biddle",
                imageUrl = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg",
                createdAt = "2021-04-12T01:33:08.833Z",
                updatedAt = "2021-12-20T20:39:19.913Z",
                url = "https://api.disneyapi.dev/characters/268",
                _v = 0
            )
        )

        val disneyActor = DisneyActor(
            name = "Anthony Biddle",
            description = "The Happiest Millionaire",
            majorAttraction = "",
            allies = "",
            enemies = "",
            image = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg"
        )
    }
}
