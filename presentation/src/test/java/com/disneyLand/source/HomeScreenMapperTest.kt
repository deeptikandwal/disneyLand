package com.disneyLand.source

import androidx.paging.PagingData
import com.disneyLand.model.DisneyListCharacter
import org.junit.Test
import com.disneyLand.model.Character;

class HomeScreenMapperTest {

    @Test
    fun `test mapping of domain model to ui model`() {
        val pagingData = PagingData.from(getDisneyListCharacters())
        val mappedData = PagingData.from(getCharactersList())
    }

    private fun getCharactersList(): List<Character> {
        return arrayListOf(
            Character(
                id = 247,
                name = "Angela",
                image = "https://static.wikia.nocookie.net/disney/images/c/cb/Angela_Fishberger.jpg"
            ),
            Character(
                id = 223,
                name = "Erica Ange",
                image = "https://static.wikia.nocookie.net/disney/images/8/88/Erica_pic.png"
            ),
            Character(
                id = 268,
                name = "Anthony Biddle",
                image = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg"
            )
        )
    }

    private fun getDisneyListCharacters(): List<DisneyListCharacter> {
        return arrayListOf(
            DisneyListCharacter(
                id = 247,
                name = "Angels",
                image = "https://static.wikia.nocookie.net/disney/images/c/cb/Angela_Fishberger.jpg"
            ),
            DisneyListCharacter(
                id = 223,
                name = "Erica Ange",
                image = "https://static.wikia.nocookie.net/disney/images/8/88/Erica_pic.png"
            ),
            DisneyListCharacter(
                id = 268,
                name = "Anthony Biddle",
                image = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg"
            )
        )
    }

}