package com.iliasahmed.userslist

import app.cash.turbine.test
import com.iliasahmed.domain.usecase.UsersListUseCase
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.UserEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UsersListViewModelTest {

    @MockK
    private lateinit var usersListUseCase: UsersListUseCase

    private lateinit var viewModel: UsersListViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val testUsers = listOf(
        UserEntity(id = 1, userName = "testUser1", userAvatar = "https://avatar.com/1"),
        UserEntity(id = 2, userName = "testUser2", userAvatar = "https://avatar.com/2")
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = UsersListViewModel(usersListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchUsers_emits_Loading_then_HasUsersList() = runTest {
        coEvery { usersListUseCase.execute(any()) } returns flowOf(Result.Success(testUsers))

        viewModel.fetchUsers("")

        viewModel.usersListUiState.test {
            assertEquals(UsersListUiState.Loading, awaitItem())
            assertEquals(UsersListUiState.HasUsersList(testUsers), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun fetchUsers_emits_Loading_then_UsersListEmpty() = runTest {
        coEvery { usersListUseCase.execute(any()) } returns flowOf(Result.Success(emptyList()))

        viewModel.fetchUsers("")

        viewModel.usersListUiState.test {
            assertEquals(UsersListUiState.Loading, awaitItem())
            assertEquals(UsersListUiState.UsersListEmpty, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun fetchUsers_emits_Loading_then_Error() = runTest {
        val errorMessage = "Something went wrong"
        coEvery { usersListUseCase.execute(any()) } returns flowOf(Result.Error(errorMessage, 403))

        viewModel.fetchUsers("")

        viewModel.usersListUiState.test {
            assertEquals(UsersListUiState.Loading, awaitItem())
            assertEquals(UsersListUiState.Error(errorMessage), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun handleAction_FetchUsersList_emits_Loading_then_HasUsersList() = runTest {
        coEvery { usersListUseCase.execute(any()) } returns flowOf(Result.Success(testUsers))

        viewModel.handleAction(UsersListUiAction.FetchUsersList)

        viewModel.usersListUiState.test {
            assertEquals(UsersListUiState.Loading, awaitItem())
            assertEquals(UsersListUiState.HasUsersList(testUsers), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun fetchUsersList_emits_Loading_state() = runTest {
        coEvery { usersListUseCase.execute(any()) } returns flowOf(Result.Loading)

        viewModel.handleAction(UsersListUiAction.FetchUsersList)

        viewModel.usersListUiState.test {
            assertEquals(UsersListUiState.Loading, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun fetchUsersList_emits_Loading_then_HasUserList_for_search_query() = runTest {
        val userName = "userTest1"
        coEvery { usersListUseCase.execute(match { it.userName == userName }) } returns flowOf(
            Result.Success(testUsers)
        )

        viewModel.fetchUsers(userName)

        viewModel.usersListUiState.test {
            assertEquals(UsersListUiState.Loading, awaitItem())
            assertEquals(UsersListUiState.HasUsersList(testUsers), awaitItem())
            cancelAndConsumeRemainingEvents()
        }

        assertEquals(userName, viewModel.searchQuery.value)
    }
}