package com.disneyLand.source

import com.disneyLand.model.Actor
import com.disneyLand.model.DisneyActor
interface DetailScreenMapper {
    fun mapToDetailScreenData(disneyActor: DisneyActor): Actor
}
