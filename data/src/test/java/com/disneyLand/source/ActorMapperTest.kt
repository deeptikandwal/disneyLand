package com.disneyLand.source

import com.BaseHelper
import com.disneyLand.dto.DisneyOriginalActor
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ActorMapperTest {

    private lateinit var actorMapper: ActorMapper

    @Before
    fun setUp() {
        actorMapper = ActorMapperImpl()
    }

    @Test
    fun `map to actor successfully Given DisneyActor When mapToDisneyActor method is called Then verify name is same`() {
        val actor = actorMapper.mapToDisneyActor(
            BaseHelper.convertJsonToModel(
                BaseHelper.getJsonFile("disney_actor.json"),
                DisneyOriginalActor::class.java
            )
        )
        Assert.assertEquals(NAME, actor.name)
    }

    @Test
    fun `map to actor successfully Given DisneyActor data is null When mapToDisneyActor method is called Then verify name is same`() {
        val actor = actorMapper.mapToDisneyActor(DisneyOriginalActor(data = null))
        Assert.assertEquals("", actor.name)
    }

    private companion object {
        const val NAME = "Abdullah"
    }
}
