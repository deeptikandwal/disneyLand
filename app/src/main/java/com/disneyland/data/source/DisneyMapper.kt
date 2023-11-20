package com.disneyland.data.source

import com.disneyland.data.dto.Characters
import com.disneyland.domain.entity.DisneyCharacter
import javax.inject.Inject

class DisneyMapper @Inject constructor() {

    fun mapToDisneyCharacter(characters: ArrayList<Characters>): List<DisneyCharacter> {
        return characters.map { character ->
            try {
                DisneyCharacter(character.id!!, character.name!!, character.imageUrl!!)
            } catch (e: Exception) {
                return listOf()
            }
        }
    }
}