package com.disneyLand.source

import com.disneyLand.dto.Characters
import com.disneyLand.model.DisneyListCharacter
import javax.inject.Inject

class DisneyMapper @Inject constructor() {
    fun mapToDisneyCharacter(list: List<Characters>): List<DisneyListCharacter> {
        return list
            .filter { (it.id != null && it.name != null && it.imageUrl != null) }
            .map { character ->
                try {
                    DisneyListCharacter(
                        character.id!!,
                        character.name!!,
                        character.imageUrl!!
                    )
                } catch (e: Exception) {
                    return listOf()
                }
            }
    }
}
