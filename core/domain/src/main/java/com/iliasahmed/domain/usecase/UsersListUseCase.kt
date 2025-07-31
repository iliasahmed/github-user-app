package com.iliasahmed.domain.usecase

import com.iliasahmed.domain.repository.GithubRepository
import com.iliasahmed.domain.utils.ApiUseCaseParams
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersListUseCase @Inject constructor(
    private val repository: GithubRepository
) : ApiUseCaseParams<UsersListUseCase.Params, List<UserEntity>> {
    override suspend fun execute(params: Params): Flow<Result<List<UserEntity>>> {
        return when {
            params.userName.isNotEmpty() -> repository.searchUsersList(params)
            else -> repository.fetchUsersList()
        }
    }

    data class Params(val userName: String)
}