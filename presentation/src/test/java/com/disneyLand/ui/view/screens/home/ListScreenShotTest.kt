package com.disneyLand.ui.view.screens.home

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.disneyLand.model.Character
import com.disneyLand.ui.components.gridItem
import org.junit.Rule
import org.junit.Test

class ListScreenShotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun launchComposable() {
        paparazzi.snapshot {
            gridItem(
                listOfCharacters,
                1
            ) {}
        }
    }

    private companion object {
        val listOfCharacters = arrayListOf(
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
}
