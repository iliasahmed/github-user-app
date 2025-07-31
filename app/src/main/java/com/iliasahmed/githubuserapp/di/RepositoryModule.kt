package com.iliasahmed.githubuserapp.di

import com.iliasahmed.data.repoimpl.GithubRepoImpl
import com.iliasahmed.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindGithubRepository(githubRepoImpl: GithubRepoImpl): GithubRepository
}