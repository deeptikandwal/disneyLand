package com.disneyLand.source

import com.disneyLand.dto.Characters
import com.disneyLand.model.DisneyListCharacter
import javax.inject.Inject

class DisneyMapperImpl @Inject constructor() : DisneyMapper {
    override fun mapToDisneyCharacter(list: List<Characters>): List<DisneyListCharacter> {
        return list.map { character ->
            DisneyListCharacter(
                character.id ?: 0,
                character.name ?: "",
                character.imageUrl ?: ""
            )
        }
    }
}
