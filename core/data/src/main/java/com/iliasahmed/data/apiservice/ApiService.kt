package com.iliasahmed.data.apiservice

import com.iliasahmed.apiresponse.ProfileApiResponse
import com.iliasahmed.apiresponse.RepoItemApiResponse
import com.iliasahmed.apiresponse.UsersApiResponse
import com.iliasahmed.apiresponse.UsersSearchApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/users/{username}/repos")
    suspend fun fetchRepoList(
        @Path("username") username: String
    ): Response<List<RepoItemApiResponse>>

    @GET("/users/{username}")
    suspend fun fetchProfile(
        @Path("username") username: String
    ): Response<ProfileApiResponse>

    @GET("/users")
    suspend fun fetchUserList(): Response<List<UsersApiResponse>>

    @GET("/search/users")
    suspend fun searchUserList(
        @Query("q") username: String
    ): Response<UsersSearchApiResponse>
}