package com.disneyLand.source

import com.disneyLand.model.Character
import com.disneyLand.model.DisneyListCharacter
import javax.inject.Inject

class HomeScreenMapperImpl @Inject constructor() : HomeScreenMapper {
    override fun mapToHomeScreenData(list: List<DisneyListCharacter>): List<Character> {
        return list.map { disneyCharacter ->
            Character(
                disneyCharacter.id,
                disneyCharacter.name.uppercase(),
                disneyCharacter.image
            )
        }
    }
}
