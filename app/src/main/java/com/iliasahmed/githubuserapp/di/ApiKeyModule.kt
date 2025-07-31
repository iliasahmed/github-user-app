package com.iliasahmed.githubuserapp.di

import com.iliasahmed.di.qualifier.AppApiKey
import com.iliasahmed.githubuserapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApiKeyModule {
    @Provides
    @AppApiKey
    fun provideApiKey(): String = BuildConfig.API_KEY
}