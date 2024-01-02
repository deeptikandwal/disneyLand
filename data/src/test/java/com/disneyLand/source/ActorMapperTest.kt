package com.disneyLand.source

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ActorMapperTest {

    private lateinit var actorMapper: ActorMapper

    @Before
    fun setUp() {
        actorMapper = ActorMapper()
    }

    @Test
    fun `map to actor successfully Given DisneyActorDto When mapToDisneyActor method is called Then verify name is same`() {
        val actor = actorMapper.mapToDisneyActor(FakeDisneyActor.getSuccessfulDisneyActorDTO())
        Assert.assertEquals(NAME, actor.name)
    }

    private companion object {
        const val NAME = "Anthony Biddle"
    }
}
