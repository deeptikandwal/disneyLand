package com.disneyLand.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.disneyLand.ui.base.CustomText
import org.junit.Rule
import org.junit.Test

class CustomTextTest {
    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun test_custom_Text() {
        testRule.setContent {
            CustomText(TEXT)
        }
        testRule.onNodeWithText(TEXT).assertIsDisplayed()
    }

    private companion object {
        const val TEXT = "Alladin"
    }
}
