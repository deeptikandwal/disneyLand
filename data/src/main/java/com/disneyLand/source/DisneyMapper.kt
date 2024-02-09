package com.disneyLand.source

import com.disneyLand.dto.Characters
import com.disneyLand.model.DisneyListCharacter

interface DisneyMapper {
    fun mapToDisneyCharacter(list: List<Characters>): List<DisneyListCharacter>
}
