package com.disneyLand.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.printToLog
import com.disneyLand.ui.base.AppTopBar
import org.junit.Rule
import org.junit.Test

class AppTopBarTest {
    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun test_custom_Text() {
        testRule.setContent {
            AppTopBar { }
        }
        testRule.onAllNodes(isRoot()).printToLog("currentLabelExists")
        testRule.onNodeWithTag(TEST_TAG)
            .assertIsDisplayed()
    }

    private companion object {
        const val TEST_TAG = "ic_back"
    }
}
