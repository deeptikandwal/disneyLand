package com.disneyLand.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.disneyLand.ui.components.SubHeaderText
import org.junit.Rule
import org.junit.Test

class SubHeaderTextTest {
    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun test_custom_Text() {
        testRule.setContent {
            SubHeaderText(TEXT)
        }
        testRule.onNodeWithText(TEXT).assertIsDisplayed()
    }

    private companion object {
        const val TEXT = "Alladin"
    }
}
