package com.iliasahmed.data.mapper

import com.iliasahmed.apiresponse.RepoItemApiResponse
import com.iliasahmed.data.utils.Mapper
import com.iliasahmed.entity.RepoItemEntity
import javax.inject.Inject

class RepoListItemMapper @Inject constructor() :
    Mapper<List<RepoItemApiResponse>, List<RepoItemEntity>> {
    override fun mapFromApiResponse(type: List<RepoItemApiResponse>): List<RepoItemEntity> {
        return type
            .filter { it.fork == false }
            .map {
                RepoItemEntity(
                    repoName = it.name ?: "EMPTY_REPO_NAME",
                    repoFullName = it.fullName ?: "EMPTY_REPO_NAME",
                    repoDescription = it.description ?: "No description found",
                    language = it.language ?: "Not Found",
                    forksCount = it.forksCount ?: 0,
                    stargazersCount = it.stargazersCount ?: 0
                )
            }
    }
}

