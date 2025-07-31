package com.iliasahmed.domain.usecase

import com.iliasahmed.domain.repository.GithubRepository
import com.iliasahmed.domain.utils.ApiUseCaseParams
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: GithubRepository
) : ApiUseCaseParams<ProfileUseCase.Params, ProfileEntity> {
    data class Params(val userName: String)

    override suspend fun execute(params: Params): Flow<Result<ProfileEntity>> {
        return repository.fetchProfile(params)
    }
}