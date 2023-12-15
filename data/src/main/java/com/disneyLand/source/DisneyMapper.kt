package com.disneyLand.source

import com.disneyLand.dto.Characters
import com.disneyLand.model.DisneyListCharacter

fun ArrayList<Characters>.mapToDisneyCharacter(): List<DisneyListCharacter> {
    return this.map { character ->
        try {
            DisneyListCharacter(character.id!!, character.name!!, character.imageUrl!!)
        } catch (e: Exception) {
            return listOf()
        }
    }
}
