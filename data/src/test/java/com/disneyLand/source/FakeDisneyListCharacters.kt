package com.disneyLand.source

import com.disneyLand.dto.Characters
import com.disneyLand.dto.DisneyCharactersList
import com.disneyLand.dto.Info
import com.disneyLand.model.DisneyListCharacter

class FakeDisneyListCharacters {
    companion object {
        val disneyListCharacters = DisneyCharactersList(
            info = Info(previousPage = "1,", nextPage = "2"),
            arrayListOf(
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
        )

        val lisOfDisneyListCharacters = arrayListOf(
            DisneyListCharacter(
                id = 247,
                name = "Angela",
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
