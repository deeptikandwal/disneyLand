package com.disneyLand.source

import androidx.paging.PagingData
import androidx.paging.map
import com.disneyLand.model.Character
import com.disneyLand.model.DisneyListCharacter

fun PagingData<DisneyListCharacter>.mapToHomeScreenData(): PagingData<Character> {
    return this.map { disneyCharacter ->
        Character(
            disneyCharacter.id,
            disneyCharacter.name.uppercase(),
            disneyCharacter.image
        )
    }
}
