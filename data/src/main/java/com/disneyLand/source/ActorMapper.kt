package com.disneyLand.source

import com.disneyLand.dto.DisneyOriginalActor
import com.disneyLand.model.DisneyActor
import javax.inject.Inject

class ActorMapper @Inject constructor() {
    fun mapToDisneyActor(disneyOriginalActor: DisneyOriginalActor): DisneyActor {
        return disneyOriginalActor.data?.run {
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
                    videoGames
                ),
                majorAttraction,
                enemy,
                ally,
                imageUrl.toString()
            )
        } ?: DisneyActor()
    }

    private fun setDescription(
        films: ArrayList<String>?,
        shortfilms: ArrayList<String>?,
        tvShows: ArrayList<String>?,
        videoGames: ArrayList<String>?
    ): String {
        val attractions = films?.joinToString(", ") { film ->
            film
        }.plus(
            shortfilms?.joinToString { shortFilm ->
                shortFilm
            }
        ).plus(
            tvShows?.joinToString { tvShow ->
                tvShow
            }
        ).plus(
            videoGames?.joinToString { videoGame ->
                videoGame
            }
        )
        return attractions
    }
}
