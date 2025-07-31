package com.iliasahmed.userslist.mock

import com.iliasahmed.domain.repository.GithubRepository
import com.iliasahmed.domain.usecase.ProfileUseCase
import com.iliasahmed.domain.usecase.RepoListUseCase
import com.iliasahmed.domain.usecase.UsersListUseCase
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.ProfileEntity
import com.iliasahmed.entity.RepoItemEntity
import com.iliasahmed.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockGithubRepository : GithubRepository {

    override suspend fun fetchUsersList(): Flow<Result<List<UserEntity>>> = flow {
        emit(Result.Success(fakeUsersList()))
    }

    override suspend fun searchUsersList(params: UsersListUseCase.Params): Flow<Result<List<UserEntity>>> =
        flow {
            emit(Result.Success(fakeUsersList().filter { it.userName.contains(params.userName) }))
        }

    override suspend fun fetchRepoList(params: RepoListUseCase.Params): Flow<Result<List<RepoItemEntity>>> =
        flow {
            emit(Result.Success(fakeReposList()))
        }

    override suspend fun fetchProfile(params: ProfileUseCase.Params): Flow<Result<ProfileEntity>> =
        flow {
            emit(Result.Success(fakeProfile(params.userName)))
        }

    private fun fakeUsersList(): List<UserEntity> = listOf(
        UserEntity(1, "https://avatar.com/1", "testUser1"),
        UserEntity(2, "https://avatar.com/2", "testUser2")
    )

    private fun fakeReposList(): List<RepoItemEntity> = listOf(
        RepoItemEntity(
            repoName = "TestRepo",
            repoFullName = "testUser/TestRepo",
            repoDescription = "Fake Repo",
            language = "Kotlin",
            forksCount = 10,
            stargazersCount = 20
        )
    )

    private fun fakeProfile(userName: String): ProfileEntity =
        ProfileEntity(
            userAvatar = "https://avatar.com/test",
            userName = userName,
            about = "test",
            repoCount = 10,
            followerCount = 100,
            followingCount = 10
        )
}