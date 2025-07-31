package com.iliasahmed.domain.utils

import kotlinx.coroutines.flow.Flow

interface UseCase

interface ApiUseCaseParams<Params, Type> : UseCase {
    suspend fun execute(params: Params): Flow<Result<Type>>
}

