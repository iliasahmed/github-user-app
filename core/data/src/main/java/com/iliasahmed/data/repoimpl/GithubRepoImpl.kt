package com.iliasahmed.data.repoimpl

import com.iliasahmed.data.apiservice.ApiService
import com.iliasahmed.data.mapper.ProfileMapper
import com.iliasahmed.data.mapper.RepoListItemMapper
import com.iliasahmed.data.mapper.UserMapper
import com.iliasahmed.data.mapper.UsersSearchListMapper
import com.iliasahmed.data.utils.NetworkBoundResource
import com.iliasahmed.data.utils.mapFromApiResponse
import com.iliasahmed.domain.repository.GithubRepository
import com.iliasahmed.domain.usecase.ProfileUseCase
import com.iliasahmed.domain.usecase.RepoListUseCase
import com.iliasahmed.domain.usecase.UsersListUseCase
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.ProfileEntity
import com.iliasahmed.entity.RepoItemEntity
import com.iliasahmed.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val networkBoundResources: NetworkBoundResource,
    private val repositoryListItemMapper: RepoListItemMapper,
    private val profileMapper: ProfileMapper,
    private val userMapper: UserMapper,
    private var usersSearchListMapper: UsersSearchListMapper
) : GithubRepository {
    override suspend fun fetchUsersList(): Flow<Result<List<UserEntity>>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchUserList()
            }, userMapper
        )
    }

    override suspend fun searchUsersList(params: UsersListUseCase.Params): Flow<Result<List<UserEntity>>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.searchUserList(params.userName)
            }, usersSearchListMapper
        )
    }

    override suspend fun fetchRepoList(params: RepoListUseCase.Params): Flow<Result<List<RepoItemEntity>>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchRepoList(params.userName)
            }, repositoryListItemMapper
        )
    }

    override suspend fun fetchProfile(params: ProfileUseCase.Params): Flow<Result<ProfileEntity>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchProfile(params.userName)
            }, profileMapper
        )
    }

}