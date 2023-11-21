package com.disneyland.presentation.source

import androidx.paging.PagingData
import androidx.paging.map
import com.disneyland.domain.model.DisneyListCharacter
import com.disneyland.presentation.model.Character
import javax.inject.Inject

class HomeScreenMapper @Inject constructor() {

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