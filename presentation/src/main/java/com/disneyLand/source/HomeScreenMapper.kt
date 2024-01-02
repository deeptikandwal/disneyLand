package com.disneyLand.source

import androidx.paging.PagingData
import androidx.paging.map
import com.disneyLand.model.Character
import com.disneyLand.model.DisneyListCharacter
import javax.inject.Inject

class HomeScreenMapper @Inject constructor(){
    fun mapToHomeScreenData(pagingData: PagingData<DisneyListCharacter>): PagingData<Character> {
        return pagingData.map { disneyCharacter ->
            Character(
                disneyCharacter.id,
                disneyCharacter.name.uppercase(),
                disneyCharacter.image
            )
        }
    }
}

