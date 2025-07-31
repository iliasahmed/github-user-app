package com.iliasahmed.userslist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.iliasahmed.designsystem.theme.GithubUserAppTheme
import com.iliasahmed.domain.repository.GithubRepository
import com.iliasahmed.entity.UserEntity
import com.iliasahmed.userslist.mock.MockGithubRepository
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UsersListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val fakeRepo: GithubRepository = MockGithubRepository()

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun userListScreen_displaysUser() {
        composeTestRule.setContent {
            GithubUserAppTheme {
                UsersListScreen(
                    userListUiSate = UsersListUiState.HasUsersList(
                        listOf(
                            UserEntity(
                                id = 1,
                                userAvatar = "https://example.com/avatar1.png",
                                userName = "testuser"
                            )
                        )
                    ),
                    onUserItemClick = {},
                    onRefreshUserList = {},
                    query = "",
                    onQueryChange = {},
                    onClearQuery = {}
                )
            }
        }
        composeTestRule
            .onNodeWithText("testuser")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("ID: 1")
            .assertIsDisplayed()
    }

    @Test
    fun loadingState_showsLoadingIndicator() {
        composeTestRule.setContent {
            UsersListScreen(
                userListUiSate = UsersListUiState.Loading,
                onUserItemClick = {},
                onRefreshUserList = {},
                query = "",
                onQueryChange = {},
                onClearQuery = {}
            )
        }

        composeTestRule
            .onNodeWithTag("loadingIndicator")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun hasUsersList_displaysUsers() {
        val testUsers = listOf(
            UserEntity(1, "https://avatar.com/1", "User One"),
            UserEntity(2, "https://avatar.com/2", "User Two")
        )

        composeTestRule.setContent {
            UsersListScreen(
                userListUiSate = UsersListUiState.HasUsersList(testUsers),
                onUserItemClick = {},
                onRefreshUserList = {},
                query = "",
                onQueryChange = {},
                onClearQuery = {}
            )
        }

        composeTestRule.onNodeWithText("User One").assertIsDisplayed()
        composeTestRule.onNodeWithText("User Two").assertIsDisplayed()
    }

    @Test
    fun usersListEmpty_showsNoUserMessage() {
        composeTestRule.setContent {
            UsersListScreen(
                userListUiSate = UsersListUiState.UsersListEmpty,
                onUserItemClick = {},
                onRefreshUserList = {},
                query = "",
                onQueryChange = {},
                onClearQuery = {}
            )
        }

        composeTestRule
            .onNodeWithText("No User List Found")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorMessage() {
        val errorMsg = "Something went wrong"
        composeTestRule.setContent {
            UsersListScreen(
                userListUiSate = UsersListUiState.Error(errorMsg),
                onUserItemClick = {},
                onRefreshUserList = {},
                query = "",
                onQueryChange = {},
                onClearQuery = {}
            )
        }

        composeTestRule
            .onNodeWithText(errorMsg)
            .assertExists()
            .assertIsDisplayed()
    }
}