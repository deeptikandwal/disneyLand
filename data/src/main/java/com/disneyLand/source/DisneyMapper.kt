package com.disneyLand.source

import com.disneyLand.dto.Characters
import com.disneyLand.model.DisneyListCharacter
import javax.inject.Inject

class DisneyMapper @Inject constructor() {
    fun mapToDisneyCharacter(list: List<Characters>): List<DisneyListCharacter> {
        return list.map { character ->
                DisneyListCharacter(
                    character.id ?: 0,
                    character.name ?: "",
                    character.imageUrl ?: ""
                )
            }
    }
}
