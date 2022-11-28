package com.oyermolov.githubapp.di

import com.oyermolov.githubapp.data.database.AppDatabase
import com.oyermolov.githubapp.data.database.GithubLocalDataSource
import com.oyermolov.githubapp.data.database.GithubLocalDataSourceImpl
import com.oyermolov.githubapp.data.remote.GithubApi
import com.oyermolov.githubapp.data.remote.GithubRemoteDataSource
import com.oyermolov.githubapp.data.remote.GithubRemoteDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
class DataSourcesModule {
    @Provides
    fun provideLocalDataSource(database: AppDatabase) : GithubLocalDataSource {
        return GithubLocalDataSourceImpl(database.repositoriesDao())
    }

    @Provides
    fun provideRemoteDataSource(api: GithubApi) : GithubRemoteDataSource {
        return GithubRemoteDataSourceImpl(api)
    }
}