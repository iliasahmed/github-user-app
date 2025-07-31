package com.iliasahmed.domain.usecase

import app.cash.turbine.test
import com.iliasahmed.domain.repository.GithubRepository
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.RepoItemEntity
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

class RepoListUseCaseTest {

    @MockK
    private lateinit var repository: GithubRepository
    private lateinit var repoListUseCase: RepoListUseCase

    private val testRepos = listOf(
        RepoItemEntity(
            repoName = "Repo1",
            repoFullName = "jetpackCompose",
            repoDescription = "It's an repository",
            language = "Kotlin",
            forksCount = 100,
            stargazersCount = 786
        ),
        RepoItemEntity(
            repoName = "Repo2",
            repoFullName = "jetpackCompose",
            repoDescription = "It's an repository",
            language = "Kotlin",
            forksCount = 10,
            stargazersCount = 76
        )
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repoListUseCase = RepoListUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun execute_returns_Success_result_from_repository() = runTest {
        val userName = "testUser"
        val params = RepoListUseCase.Params(userName = userName)
        coEvery { repository.fetchRepoList(params) } returns flowOf(Result.Success(testRepos))

        val result = repoListUseCase.execute(params)

        result.test {
            assertEquals(Result.Success(testRepos), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun execute_returns_Error_result_from_repository() = runTest {
        val userName = "testUser"
        val errorMessage = "Failed to fetch repositories"
        val params = RepoListUseCase.Params(userName = userName)
        coEvery { repository.fetchRepoList(params) } returns flowOf<Result<List<RepoItemEntity>>>(
            Result.Error(errorMessage, 404)
        )

        val result = repoListUseCase.execute(params)

        result.test {
            assertEquals(Result.Error(errorMessage, 404), awaitItem())
            awaitComplete()
        }
    }
}
