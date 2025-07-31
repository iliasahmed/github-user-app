package com.iliasahmed.domain.usecase

import app.cash.turbine.test
import com.iliasahmed.domain.repository.GithubRepository
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.ProfileEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProfileUseCaseTest {

    @MockK
    private lateinit var repository: GithubRepository
    private lateinit var profileUseCase: ProfileUseCase

    private val testProfile = ProfileEntity(
        userAvatar = "https://avatar.com",
        userName = "iliasahmed",
        about = "Android Dev",
        repoCount = 10,
        followerCount = 100,
        followingCount = 50
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        profileUseCase = ProfileUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun execute_returns_Success_result_from_repository() = runTest {
        val params = ProfileUseCase.Params(userName = "ilias")
        coEvery { repository.fetchProfile(params) } returns flowOf(Result.Success(testProfile))

        val result = profileUseCase.execute(params)

        result.test {
            assertEquals(Result.Success(testProfile), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun execute_returns_Error_result_from_repository() = runTest {
        val errorMessage = "Something went wrong"
        val params = ProfileUseCase.Params(userName = "ilias")
        coEvery { repository.fetchProfile(params) } returns flowOf(Result.Error(errorMessage, 403))

        val result = profileUseCase.execute(params)

        result.test {
            assertEquals(Result.Error<ProfileEntity>(errorMessage, 403), awaitItem())
            awaitComplete()
        }
    }
}
