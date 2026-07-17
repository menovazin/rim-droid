package com.rim.droid.presentation.smoke

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rim.droid.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginToHomeSmokeTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val resetSessionRule = ResetSessionRule()

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun login_navigates_to_home_characters_section() {
        composeRule.onNodeWithText("Sign in to open the portal").assertIsDisplayed()

        composeRule.onNodeWithText("Name").performTextInput("Morty")
        composeRule.onNodeWithText("Sign in").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule
                .onAllNodesWithText("Characters")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        // Top bar + drawer both expose "Characters"; assert at least one is shown.
        composeRule.onAllNodesWithText("Characters").onFirst().assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Menu").assertIsDisplayed()
    }

    @Test
    fun home_list_shows_retry_when_catalog_fails() {
        composeRule.onNodeWithText("Name").performTextInput("Rick")
        composeRule.onNodeWithText("Sign in").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule
                .onAllNodesWithText("Retry")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeRule.onNodeWithText("Something went wrong. Please try again.").assertIsDisplayed()
        composeRule.onNodeWithText("Retry").assertIsDisplayed()
    }
}
