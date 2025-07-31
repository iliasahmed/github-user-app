package com.iliasahmed.domain.repository

import com.iliasahmed.domain.usecase.ProfileUseCase
import com.iliasahmed.domain.usecase.RepoListUseCase
import com.iliasahmed.domain.usecase.UsersListUseCase
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.ProfileEntity
import com.iliasahmed.entity.RepoItemEntity
import com.iliasahmed.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun fetchUsersList(): Flow<Result<List<UserEntity>>>
    suspend fun searchUsersList(params: UsersListUseCase.Params): Flow<Result<List<UserEntity>>>
    suspend fun fetchRepoList(params: RepoListUseCase.Params): Flow<Result<List<RepoItemEntity>>>
    suspend fun fetchProfile(params: ProfileUseCase.Params): Flow<Result<ProfileEntity>>
}