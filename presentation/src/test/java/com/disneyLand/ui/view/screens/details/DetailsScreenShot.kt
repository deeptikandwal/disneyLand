package com.disneyLand.ui.view.screens.details

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.disneyLand.model.Actor
import org.junit.Rule
import org.junit.Test
import java.util.logging.LogManager

class DetailsScreenShot {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun launchComposable() {
        LogManager.getLogManager().reset()
        paparazzi.snapshot {
            setActorName(actor)
        }
    }

    private companion object {
        val actor = Actor(
            name = "Anthony Biddle",
            description = "",
            image = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg"
        )
    }
}
