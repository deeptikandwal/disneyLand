package com.disneyLand.source

import com.disneyLand.dto.Characters
import com.disneyLand.model.DisneyListCharacter
import javax.inject.Inject

class DisneyMapper @Inject constructor() {

    fun mapToDisneyCharacter(characters: ArrayList<Characters>): List<DisneyListCharacter> {
        return characters.map { character ->
            try {
                DisneyListCharacter(character.id!!, character.name!!, character.imageUrl!!)
            } catch (e: Exception) {
                return listOf()
            }
        }
    }

}