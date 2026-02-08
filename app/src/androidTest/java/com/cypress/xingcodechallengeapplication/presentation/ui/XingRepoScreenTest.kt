package com.cypress.xingcodechallengeapplication.presentation.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.cypress.xingcodechallengeapplication.domain.XingRepoModel
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class XingRepoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun xingRepoScreen_displaysItems() {

        val fakeItems = listOf(
            XingRepoModel(
                id = 1,
                name = "Repo 1",
                description = "Description 1",
                ownerLogin = "Owner1",
                ownerAvatarUrl = "",
                isFork = false
            ),
            XingRepoModel(
                id = 2,
                name = "Repo 2",
                description = "Description 2",
                ownerLogin = "Owner2",
                ownerAvatarUrl = "",
                isFork = true
            )
        )

        val testFlow = flowOf(PagingData.from(fakeItems))

        composeTestRule.setContent {
            val lazyItems = testFlow.collectAsLazyPagingItems()
            XingRepoScreen(
                xingLazyPagingItems = lazyItems,
                onNavigate = {}
            )
        }


        composeTestRule.onNodeWithText("Repo 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Repo 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Owner: Owner1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Owner: Owner2").assertIsDisplayed()
    }

    @Test
    fun xingRepoScreen_displaysEmptyState_whenNoItems() {

        val emptyFlow = flowOf(PagingData.from(emptyList<XingRepoModel>()))

        composeTestRule.setContent {
            val lazyItems = emptyFlow.collectAsLazyPagingItems()
            XingRepoScreen(
                xingLazyPagingItems = lazyItems,
                onNavigate = {}
            )
        }

        composeTestRule.onNodeWithText("Empty list").assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }
}