@file:OptIn(ExperimentalCoroutinesApi::class)

package com.iliasahmed.profile

import app.cash.turbine.test
import com.iliasahmed.domain.usecase.ProfileUseCase
import com.iliasahmed.domain.usecase.RepoListUseCase
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.ProfileEntity
import com.iliasahmed.entity.RepoItemEntity
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
class ProfileViewModelTest {

    @MockK
    lateinit var profileUseCase: ProfileUseCase

    @MockK
    lateinit var repoListUseCase: RepoListUseCase

    private lateinit var viewModel: ProfileViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val testProfile = ProfileEntity(
        userAvatar = "https://avatar.com/1",
        userName = "testUser",
        about = "Android Developer",
        repoCount = 24,
        followerCount = 24,
        followingCount = 24
    )

    private val testRepos = listOf(
        RepoItemEntity(
            repoName = "test",
            repoFullName = "jetpackCompose",
            repoDescription = "It's an repository",
            language = "Kotlin",
            forksCount = 100,
            stargazersCount = 786
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = ProfileViewModel(profileUseCase, repoListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun setUserName_emits_loading_then_Success_when_both_API_succeed() = runTest {
        val username = "testUser"
        coEvery { profileUseCase.execute(any()) } returns flowOf(Result.Success(testProfile))
        coEvery { repoListUseCase.execute(any()) } returns flowOf(Result.Success(testRepos))

        viewModel.setUserName(username)

        viewModel.profileUiState.test {
            assertEquals(ProfileUiState.Loading, awaitItem())
            assertEquals(ProfileUiState.Success(testProfile, testRepos), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun setUserName_emits_Error_when_profile_API_fails() = runTest {
        val username = "testUser"
        val errorMessage = "Profile error"
        coEvery { profileUseCase.execute(any()) } returns flowOf(Result.Error(errorMessage, 403))
        coEvery { repoListUseCase.execute(any()) } returns flowOf(Result.Success(testRepos))

        viewModel.setUserName(username)

        viewModel.profileUiState.test {
            assertEquals(ProfileUiState.Loading, awaitItem())
            assertEquals(ProfileUiState.Error(errorMessage), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun setUserName_emits_Error_when_repo_API_fails() = runTest {
        val username = "testUser"
        val errorMessage = "Repo error"
        coEvery { profileUseCase.execute(any()) } returns flowOf(Result.Success(testProfile))
        coEvery { repoListUseCase.execute(any()) } returns flowOf(Result.Error(errorMessage, 403))

        viewModel.setUserName(username)

        viewModel.profileUiState.test {
            assertEquals(ProfileUiState.Loading, awaitItem())
            assertEquals(ProfileUiState.Error(errorMessage), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun setUserName_emits_Loading_when_either_API_is_still_loading() = runTest {
        val username = "testUser"
        coEvery { profileUseCase.execute(any()) } returns flowOf(Result.Loading)
        coEvery { repoListUseCase.execute(any()) } returns flowOf(Result.Loading)

        viewModel.setUserName(username)

        viewModel.profileUiState.test {
            assertEquals(ProfileUiState.Loading, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun handleAction_FetchProfile_triggers_the_fetch_again() = runTest {
        val username = "testUser"
        coEvery { profileUseCase.execute(any()) } returns flowOf(Result.Success(testProfile))
        coEvery { repoListUseCase.execute(any()) } returns flowOf(Result.Success(testRepos))

        viewModel.profileUiState.test {
            viewModel.setUserName(username)
            assertEquals(ProfileUiState.Loading, awaitItem())
            assertEquals(ProfileUiState.Success(testProfile, testRepos), awaitItem())

            viewModel.handleAction(ProfileUiAction.FetchProfile)
            assertEquals(ProfileUiState.Loading, awaitItem())
            assertEquals(ProfileUiState.Success(testProfile, testRepos), awaitItem())

            cancelAndConsumeRemainingEvents()
        }
    }
}