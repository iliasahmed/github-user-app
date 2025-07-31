package com.iliasahmed.domain.usecase

import app.cash.turbine.test
import com.iliasahmed.domain.repository.GithubRepository
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.UserEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UsersListUseCaseTest {
    @MockK
    private lateinit var repository: GithubRepository
    private lateinit var useCase: UsersListUseCase

    private val testUsers = listOf(
        UserEntity(id = 1, userName = "user1", userAvatar = "https://avatar.com/u1"),
        UserEntity(id = 2, userName = "user2", userAvatar = "https://avatar.com/u2"),
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = UsersListUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun execute_returns_success_when_userName_is_not_empty() = runTest {
        val params = UsersListUseCase.Params(userName = "test")
        coEvery { repository.searchUsersList(params) } returns flowOf(Result.Success(testUsers))

        val resultFlow = useCase.execute(params)

        resultFlow.test {
            assertEquals(Result.Success(testUsers), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun execute_returns_success_when_userName_is_empty() = runTest {
        val params = UsersListUseCase.Params(userName = "")
        coEvery { repository.fetchUsersList() } returns flowOf(Result.Success(testUsers))

        val resultFlow = useCase.execute(params)

        resultFlow.test {
            assertEquals(Result.Success(testUsers), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun execute_returns_error_when_repository_fails() = runTest {
        val errorMessage = "Network error"
        val params = UsersListUseCase.Params(userName = "fail")
        coEvery { repository.searchUsersList(params) } returns flowOf(
            Result.Error(
                errorMessage,
                403
            )
        )

        val resultFlow = useCase.execute(params)

        resultFlow.test {
            assertEquals(Result.Error(errorMessage, 403), awaitItem())
            awaitComplete()
        }
    }
}
