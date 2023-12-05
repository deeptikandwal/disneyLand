package com.disneyLand.source

import com.disneyLand.dto.DisneyActorDto
import com.disneyLand.model.DisneyActor
import javax.inject.Inject

class ActorMapper @Inject constructor() {

    fun mapToDisneyActor(disneyActorDto: DisneyActorDto?): DisneyActor? {
        var disneyActor: DisneyActor? = null
        disneyActorDto?.data?.run {
            disneyActor = try {
                val enemy = enemies?.joinToString {
                    it
                }.toString()

                val ally = allies?.joinToString {
                    it
                }.toString()

                val majorAttraction = parkAttractions?.joinToString {
                    it
                }.toString()

                DisneyActor(
                    name.toString(),
                    setDescription(
                        films,
                        shortFilms,
                        tvShows,
                        videoGames,
                    ),
                    majorAttraction,
                    enemy,
                    ally,
                    imageUrl.toString()
                )
            } catch (e: Exception) {
                DisneyActor("", "", "", "", "","")
            }
        }
        return disneyActor
    }

    private fun setDescription(
        films: ArrayList<String>?,
        shortfilms: ArrayList<String>?,
        tvShows: ArrayList<String>?,
        videoGames: ArrayList<String>?,
    ): String {
        val attractions = films?.joinToString(", ") { film ->
            film
        }.plus(shortfilms?.joinToString { shortFilm ->
            shortFilm
        }).plus(tvShows?.joinToString { tvShow ->
            tvShow
        }).plus(videoGames?.joinToString { videoGame ->
            videoGame
        })
        return attractions
    }

}