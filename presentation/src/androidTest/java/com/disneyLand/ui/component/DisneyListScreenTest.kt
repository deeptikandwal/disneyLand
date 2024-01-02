package com.disneyLand.ui.component

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.disneyLand.model.Actor
import com.disneyLand.ui.view.screens.details.DisneyDetailScreen
import org.junit.Rule
import org.junit.Test

class DisneyListScreenTest {
    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun test_disney_detail_screen() {
        testRule.setContent {
            DisneyDetailScreen(ID){}
        }
        testRule.run {
            onNodeWithText(NAME).assertExists()
            onNodeWithText(ENEMY).assertExists()
        }
    }

    private companion object {
        const val NAME = "Alladin"
        const val ENEMY = "Jafar"
        const val ID = "208"
    }
}
