package com.disneyland.data.source

import com.disneyland.data.dto.Actor
import com.disneyland.data.dto.DisneyActorDto
import com.disneyland.data.dto.Info
import com.disneyland.domain.model.DisneyActor

class FakeDisneyActor {

    companion object {
        fun getSuccessfulDisneyActorDTO(): DisneyActorDto {
            return DisneyActorDto(
                info = Info(count = 1, totalPages = 1),
                data = Actor(
                    id = 268,
                    films = arrayListOf("The Happiest Millionaire"),
                    shortFilms = arrayListOf(),
                    tvShows = arrayListOf(),
                    videoGames = arrayListOf(),
                    parkAttractions = arrayListOf(),
                    allies = arrayListOf(),
                    enemies = arrayListOf(),
                    sourceUrl = "https://disney.fandom.com/wiki/Anthony_Biddle",
                    name = "Anthony Biddle",
                    imageUrl = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg",
                    createdAt = "2021-04-12T01:33:08.833Z",
                    updatedAt = "2021-12-20T20:39:19.913Z",
                    url = "https://api.disneyapi.dev/characters/268",
                    _v = 0
                )
            )
        }

        fun getSuccessfulDisneyActor(): DisneyActor {
            return DisneyActor(
                name = "Anthony Biddle",
                description = "The Happiest Millionaire",
                majorAttraction = "",
                allies = "",
                enemies = "",
                image = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg"
            )
        }
    }
}