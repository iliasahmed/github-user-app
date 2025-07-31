package com.iliasahmed.githubuserapp.di

import com.iliasahmed.di.qualifier.AppBaseUrl
import com.iliasahmed.githubuserapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BaseUrlModule {
    @Provides
    @AppBaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
}