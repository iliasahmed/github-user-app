package com.iliasahmed.data.repoimpl

import app.cash.turbine.test
import com.iliasahmed.apiresponse.ProfileApiResponse
import com.iliasahmed.apiresponse.RepoItemApiResponse
import com.iliasahmed.apiresponse.UsersApiResponse
import com.iliasahmed.apiresponse.UsersSearchApiResponse
import com.iliasahmed.data.apiservice.ApiService
import com.iliasahmed.data.mapper.ProfileMapper
import com.iliasahmed.data.mapper.RepoListItemMapper
import com.iliasahmed.data.mapper.UserMapper
import com.iliasahmed.data.mapper.UsersSearchListMapper
import com.iliasahmed.data.models.profileApiResponse
import com.iliasahmed.data.models.testProfile
import com.iliasahmed.data.models.testRepoList
import com.iliasahmed.data.models.testUserList
import com.iliasahmed.data.models.userSearchApiResponse
import com.iliasahmed.data.utils.NetworkBoundResource
import com.iliasahmed.domain.usecase.ProfileUseCase
import com.iliasahmed.domain.usecase.RepoListUseCase
import com.iliasahmed.domain.usecase.UsersListUseCase
import com.iliasahmed.domain.utils.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GithubRepoImplTest {

    @MockK
    private lateinit var apiService: ApiService

    @MockK
    private lateinit var networkBoundResource: NetworkBoundResource

    @MockK
    private lateinit var repoListItemMapper: RepoListItemMapper

    @MockK
    private lateinit var profileMapper: ProfileMapper

    @MockK
    private lateinit var userMapper: UserMapper

    @MockK
    private lateinit var usersSearchListMapper: UsersSearchListMapper

    private lateinit var repoImpl: GithubRepoImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repoImpl = GithubRepoImpl(
            apiService,
            networkBoundResource,
            repoListItemMapper,
            profileMapper,
            userMapper,
            usersSearchListMapper
        )
    }

    @Test
    fun fetchUsersList_returns_Success() = runTest {
        val apiData = listOf<UsersApiResponse>()
        val mappedData = testUserList

        coEvery { networkBoundResource.downloadData<List<UsersApiResponse>>(any()) } returns flowOf(
            Result.Success(apiData)
        )
        every { userMapper.mapFromApiResponse(apiData) } returns mappedData

        repoImpl.fetchUsersList().test {
            assertEquals(Result.Success(mappedData), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun searchUsersList_returns_Success() = runTest {
        val mappedData = testUserList
        val userName = "ilias"
        coEvery {
            networkBoundResource.downloadData<UsersSearchApiResponse>(any())
        } returns flowOf(Result.Success(userSearchApiResponse))
        every { usersSearchListMapper.mapFromApiResponse(userSearchApiResponse) } returns testUserList
        val result = repoImpl.searchUsersList(UsersListUseCase.Params(userName))

        result.test {
            assertEquals(Result.Success(mappedData), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun fetchRepoList_returns_Success() = runTest {
        val userName = "ilias"
        val apiData: List<RepoItemApiResponse> = emptyList()
        val mappedData = testRepoList
        coEvery { networkBoundResource.downloadData<List<RepoItemApiResponse>>(any()) } returns flowOf(
            Result.Success(apiData)
        )
        every { repoListItemMapper.mapFromApiResponse(apiData) } returns mappedData

        val result = repoImpl.fetchRepoList(RepoListUseCase.Params(userName))

        result.test {
            assertEquals(Result.Success(mappedData), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun fetchProfile_returns_Success() = runTest {
        val apiResult = Result.Success(profileApiResponse)

        coEvery {
            networkBoundResource.downloadData<ProfileApiResponse>(any())
        } returns flowOf(apiResult)

        every { profileMapper.mapFromApiResponse(profileApiResponse) } returns testProfile

        val result = repoImpl.fetchProfile(ProfileUseCase.Params("ilias"))

        result.test {
            assertEquals(Result.Success(testProfile), awaitItem())
            awaitComplete()
        }
    }
}
