package com.disneyLand.source

import com.disneyLand.dto.DisneyOriginalActor
import com.disneyLand.model.DisneyActor

interface ActorMapper {
    fun mapToDisneyActor(disneyOriginalActor: DisneyOriginalActor): DisneyActor
}
