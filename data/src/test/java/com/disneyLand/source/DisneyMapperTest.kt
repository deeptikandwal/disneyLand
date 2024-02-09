package com.disneyLand.source

import com.BaseHelper
import com.disneyLand.dto.Characters
import com.disneyLand.dto.DisneyCharactersList
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DisneyMapperTest {
    private lateinit var disneyMapper: DisneyMapper

    @Before
    fun setUp() {
        disneyMapper = DisneyMapperImpl()
    }

    @Test
    fun `map to disney character list successfully Given Characters list When mapToDisneyCharacter method is called Then verify size`() {
        val disneyCharactersListResponseModel = BaseHelper.convertJsonToModel(
            BaseHelper.getJsonFile("disney_actor_list.json"),
            DisneyCharactersList::class.java
        )
        val charactersList = disneyCharactersListResponseModel.data
        val disneyListCharacters = disneyMapper.mapToDisneyCharacter(charactersList)
        Assert.assertEquals(40, disneyListCharacters.size)
    }

    @Test
    fun `map to disney character list successfully Given Characters list has null items`() {
        val disneyListCharacters =
            disneyMapper.mapToDisneyCharacter(arrayListOf<Characters>(Characters(null)))
        Assert.assertEquals(1, disneyListCharacters.size)
    }
}
