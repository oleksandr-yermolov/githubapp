package com.oyermolov.githubapp.di

import com.oyermolov.githubapp.data.GithubRepository
import com.oyermolov.githubapp.data.GithubRepositoryImpl
import com.oyermolov.githubapp.data.database.GithubLocalDataSource
import com.oyermolov.githubapp.data.mappers.ApiToDomainRepositoryMapper
import com.oyermolov.githubapp.data.mappers.ApiToDomainUserMapper
import com.oyermolov.githubapp.data.mappers.DatabaseToDomainRepositoryMapper
import com.oyermolov.githubapp.data.mappers.DomainToDatabaseRepositoryMapper
import com.oyermolov.githubapp.data.remote.GithubRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {
    @Provides
    fun provideGithubRepository(
        remoteDataSource: GithubRemoteDataSource,
        localDataSource: GithubLocalDataSource
    ) : GithubRepository {
        return GithubRepositoryImpl(
            remoteDataSource,
            localDataSource,
            ApiToDomainRepositoryMapper(),
            DatabaseToDomainRepositoryMapper(),
            DomainToDatabaseRepositoryMapper(),
            ApiToDomainUserMapper()
        )
    }
}