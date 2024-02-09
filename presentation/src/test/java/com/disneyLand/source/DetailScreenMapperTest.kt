package com.disneyLand.source

import com.disneyLand.model.Actor
import com.disneyLand.model.DisneyActor
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DetailScreenMapperTest {
    private lateinit var detailScreenMapper: DetailScreenMapperImpl

    @Before
    fun setUp() {
        detailScreenMapper = DetailScreenMapperImpl()
    }

    @Test
    fun `map disneyActor to Actor UI model successfully`() {
        val mappedData = detailScreenMapper.mapToDetailScreenData(disneyActor)

        Assert.assertEquals(actor.name, mappedData.name)
    }

    private companion object {
        val disneyActor = DisneyActor(
            name = "Anthony Biddle",
            description = "The Happiest Millionaire",
            majorAttraction = "The Happiest Millionaire",
            image = "",
            allies = "Cordelia Biddle (wife), Tonny Biddle",
            enemies = "Tonny Biddle"
        )

        val actor = Actor(
            name = "ANTHONY BIDDLE",
            description = "This character featured in films, tv shows and video games like : The Happiest Millionaire",
            image = ""
        )
    }
}
