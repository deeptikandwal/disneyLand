package com.disneyLand.source

import com.disneyLand.dto.Characters
import com.disneyLand.dto.DisneyCharactersListDto
import com.disneyLand.model.DisneyListCharacter

class FakeDisneyListCharacters {
    companion object {
        fun getDisneyListCharactersDto(): DisneyCharactersListDto {
            return DisneyCharactersListDto(
                info = null,
                arrayListOf(
                    Characters(
                        id = 247,
                        name = "Angels",
                        sourceUrl = "https://static.wikia.nocookie.net/disney/images/c/cb/Angela_Fishberger.jpg"
                    ),
                    Characters(
                        id = 223,
                        name = "Erica Ange",
                        sourceUrl = "https://static.wikia.nocookie.net/disney/images/8/88/Erica_pic.png"
                    ),
                    Characters(
                        id = 268,
                        name = "Anthony Biddle",
                        sourceUrl = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg"
                    )
                )
            )
        }

        fun getDisneyListCharacters(): List<DisneyListCharacter> {
            return arrayListOf(
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
}
