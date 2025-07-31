package com.iliasahmed.domain.usecase

import com.iliasahmed.domain.repository.GithubRepository
import com.iliasahmed.domain.utils.ApiUseCaseParams
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.RepoItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoListUseCase @Inject constructor(
    private val repository: GithubRepository
) : ApiUseCaseParams<RepoListUseCase.Params, List<RepoItemEntity>> {
    override suspend fun execute(params: Params): Flow<Result<List<RepoItemEntity>>> {
        return repository.fetchRepoList(params)
    }

    data class Params(val userName: String)
}