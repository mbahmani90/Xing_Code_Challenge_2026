package com.cypress.xingcodechallengeapplication.presentation.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cypress.xingcodechallengeapplication.domain.XingDetailsModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class XingDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleRepo = XingDetailsModel(
        id = 1,
        name = "Sample Repo",
        ownerLogin = "octocat",
        description = "This is a sample repository",
        stars = 42,
        forks = 10,
        language = "Kotlin",
        ownerAvatarUrl = ""
    )

    @Test
    fun xingDetailsScreen_showsLoading_whenRepoIsNull() {
        composeTestRule.setContent {
            XingDetailsScreen(repo = null)
        }

        composeTestRule.onNodeWithTag("LoadingIndicator")
            .assertIsDisplayed()
    }

    @Test
    fun xingDetailsScreen_displaysRepoData() {
        composeTestRule.setContent {
            XingDetailsScreen(repo = sampleRepo)
        }

        composeTestRule.onNodeWithText("Sample Repo").assertIsDisplayed()

        composeTestRule.onNodeWithText("Owner: octocat").assertIsDisplayed()

        composeTestRule.onNodeWithText("This is a sample repository").assertIsDisplayed()

        composeTestRule.onNodeWithText("Language: Kotlin").assertIsDisplayed()

    }

    @Test
    fun xingDetailsScreen_backButton_callsOnBackClick() {
        var clicked = false

        composeTestRule.setContent {
            XingDetailsScreen(
                repo = sampleRepo,
                onBackClick = { clicked = true }
            )
        }

        composeTestRule.onNode(
            hasContentDescription("Back")
        ).performClick()

        assert(clicked)
    }
}
