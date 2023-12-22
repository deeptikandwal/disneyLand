package com.disneyLand.source

import org.junit.Assert
import org.junit.Test

class ActorMapperTest {
    @Test
    fun `map to actor successfully Given DisneyActorDto When mapToDisneyActor method is called Then verify name is same`() {
        val actor = FakeDisneyActor.getSuccessfulDisneyActorDTO().mapToDisneyActor()
        Assert.assertEquals(NAME, actor?.name)
    }

    private companion object {
        const val NAME = "Anthony Biddle"
    }
}
