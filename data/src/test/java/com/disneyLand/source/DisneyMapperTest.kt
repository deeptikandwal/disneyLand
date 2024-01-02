package com.disneyLand.source

import com.disneyLand.dto.Characters
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DisneyMapperTest {
    private lateinit var disneyMapper: DisneyMapper

    @Before
    fun setUp() {
        disneyMapper = DisneyMapper()
    }

    @Test
    fun `map to disney character list successfully Given Characters list When mapToDisneyCharacter method is called Then verify size`() {
        val disneyListCharacters = disneyMapper.mapToDisneyCharacter(charactersList)
        Assert.assertEquals(3, disneyListCharacters.size)
    }

    @Test
    fun `map to disney character list successfully Given Characters list has null items`() {
        val disneyListCharacters =
            disneyMapper.mapToDisneyCharacter(arrayListOf<Characters>(Characters(null)))
        Assert.assertEquals(0, disneyListCharacters.size)
    }

    private companion object {
        val charactersList = arrayListOf(
            Characters(
                id = 247,
                name = "Angela",
                sourceUrl = "https://static.wikia.nocookie.net/disney/images/c/cb/Angela_Fishberger.jpg",
                imageUrl = "https://static.wikia.nocookie.net/disney/images/c/cb/Angela_Fishberger.jpg"
            ),
            Characters(
                id = 223,
                name = "Erica Ange",
                sourceUrl = "https://static.wikia.nocookie.net/disney/images/8/88/Erica_pic.png",
                imageUrl = "https://static.wikia.nocookie.net/disney/images/8/88/Erica_pic.png"
            ),
            Characters(
                id = 268,
                name = "Anthony Biddle",
                sourceUrl = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg",
                imageUrl = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg"
            )
        )

    }
}
