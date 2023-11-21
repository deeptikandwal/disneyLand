package com.disneyland.data.source

import com.disneyland.AppConstants
import com.disneyland.data.dto.DisneyActorDto
import com.disneyland.domain.entity.DisneyActor
import javax.inject.Inject

class ActorMapper @Inject constructor() {

    fun mapToDisneyActor(disneyActorDto: DisneyActorDto?): DisneyActor? {
        var disneyActor: DisneyActor? = null
        disneyActorDto?.data?.run {
            disneyActor = try {
                DisneyActor(
                    name!!,
                    setDescription(films, shortFilms, tvShows, videoGames, parkAttractions),
                    imageUrl!!
                )
            } catch (e: Exception) {
                DisneyActor("", "", "")
            }
        }
        return disneyActor
    }

    private fun setDescription(
        films: ArrayList<String>?,
        shortfilms: ArrayList<String>?,
        tvShows: ArrayList<String>?,
        videoGames: ArrayList<String>?,
        parkAttractions: ArrayList<String>?,
    ): String {
        val films =
            films?.joinToString(", ") {
                it.plus(shortfilms?.joinToString(", ")).plus(tvShows?.joinToString(", ")).plus(
                    videoGames?.joinToString(
                        ", "
                    )
                )
            }

        return AppConstants.DESCRIPTION + films + AppConstants.DESCRIPTION2 + parkAttractions?.joinToString(
            ","
        )
    }
}