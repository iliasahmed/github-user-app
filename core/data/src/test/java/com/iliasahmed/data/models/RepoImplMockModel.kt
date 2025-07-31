package com.iliasahmed.data.models

import com.iliasahmed.apiresponse.ProfileApiResponse
import com.iliasahmed.apiresponse.UsersSearchApiResponse
import com.iliasahmed.entity.ProfileEntity
import com.iliasahmed.entity.RepoItemEntity
import com.iliasahmed.entity.UserEntity

val testProfile = ProfileEntity(
    userAvatar = "https://avatar.com",
    userName = "iliasahmed",
    about = "Android Dev",
    repoCount = 10,
    followerCount = 100,
    followingCount = 50
)

val testUserList = listOf(
    UserEntity(
        id = 1,
        userAvatar = "https://avatar.com",
        userName = "ilias"
    )
)

val testRepoList =
    listOf(
        RepoItemEntity(
            repoName = "test",
            repoFullName = "jetpackCompose",
            repoDescription = "It's an repository",
            language = "Kotlin",
            forksCount = 100,
            stargazersCount = 786
        )
    )

val userSearchApiResponse = UsersSearchApiResponse(
    totalCount = 20,
    incompleteResults = false,
    items = listOf()
)

val profileApiResponse = ProfileApiResponse(
    login = "ilias",
    id = 123,
    nodeId = "MDQ6VXNlcjEyMw==",
    avatarUrl = "https://avatar.com/ilias",
    gravatarId = null,
    url = "https://api.github.com/users/ilias",
    htmlUrl = "https://github.com/ilias",
    followersUrl = "https://api.github.com/users/ilias/followers",
    followingUrl = "https://api.github.com/users/ilias/following{/other_user}",
    gistsUrl = "https://api.github.com/users/ilias/gists{/gist_id}",
    starredUrl = "https://api.github.com/users/ilias/starred{/owner}{/repo}",
    subscriptionsUrl = "https://api.github.com/users/ilias/subscriptions",
    organizationsUrl = "https://api.github.com/users/ilias/orgs",
    reposUrl = "https://api.github.com/users/ilias/repos",
    type = "User",
    userViewType = null,
    siteAdmin = false,
    name = "Ilias Ahmed",
    company = "iliasahmed",
    blog = "https://ilias.dev",
    location = "Dhaka, Bangladesh",
    email = "ilias@example.com",
    hireable = true,
    bio = "Android Developer",
    twitterUsername = "iliasahmed",
    publicRepos = 24,
    publicGists = 3,
    followers = 100,
    following = 50,
    createdAt = "2020-01-01T00:00:00Z",
    updatedAt = "2025-08-01T00:00:00Z"
)

val profileApiResponseWithNull = ProfileApiResponse(
    login = null,
    id = null,
    nodeId = null,
    avatarUrl = null,
    gravatarId = null,
    url = null,
    htmlUrl = null,
    followersUrl = null,
    followingUrl = null,
    gistsUrl = null,
    starredUrl = null,
    subscriptionsUrl = null,
    organizationsUrl = null,
    reposUrl = null,
    type = null,
    userViewType = null,
    siteAdmin = null,
    name = null,
    company = null,
    blog = null,
    location = null,
    email = null,
    hireable = null,
    bio = null,
    twitterUsername = null,
    publicRepos = null,
    publicGists = null,
    followers = null,
    following = null,
    createdAt = null,
    updatedAt = null
)
