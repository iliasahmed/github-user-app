package com.iliasahmed.entity

data class RepoItemEntity(
    val repoName: String,
    val repoFullName: String,
    val repoDescription: String,
    val language: String,
    val forksCount: Int,
    val stargazersCount: Int,
) {
    constructor() : this(
        repoName = "test",
        repoFullName = "jetpackCompose",
        repoDescription = "It's an repository",
        language = "Kotlin",
        forksCount = 100,
        stargazersCount = 786
    )
}