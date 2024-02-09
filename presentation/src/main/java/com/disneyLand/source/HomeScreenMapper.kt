package com.disneyLand.source

import com.disneyLand.model.Character
import com.disneyLand.model.DisneyListCharacter

interface HomeScreenMapper {
    fun mapToHomeScreenData(list: List<DisneyListCharacter>): List<Character>
}
