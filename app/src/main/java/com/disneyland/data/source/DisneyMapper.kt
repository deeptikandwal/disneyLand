package com.disneyland.data.source

import com.disneyland.data.dto.Characters
import com.disneyland.domain.entity.DisneyListCharacter
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