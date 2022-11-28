package com.oyermolov.githubapp.data

import com.oyermolov.githubapp.data.database.GithubLocalDataSource
import com.oyermolov.githubapp.data.mappers.ApiToDomainRepositoryMapper
import com.oyermolov.githubapp.data.mappers.ApiToDomainUserMapper
import com.oyermolov.githubapp.data.mappers.DatabaseToDomainRepositoryMapper
import com.oyermolov.githubapp.data.mappers.DomainToDatabaseRepositoryMapper
import com.oyermolov.githubapp.data.remote.GithubRemoteDataSource
import com.oyermolov.githubapp.domain.RepositoryEntity
import com.oyermolov.githubapp.domain.UserEntity
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val remoteDataSource: GithubRemoteDataSource,
    private val localDataSource: GithubLocalDataSource,
    private val apiToDomainRepositoryMapper: ApiToDomainRepositoryMapper,
    private val databaseToDomainRepositoryMapper: DatabaseToDomainRepositoryMapper,
    private val domainToDatabaseRepositoryMapper: DomainToDatabaseRepositoryMapper,
    private val apiToDomainUserMapper: ApiToDomainUserMapper
) : GithubRepository {

    override suspend fun getUser(username: String): Result<UserEntity> {
        try {
            val user = remoteDataSource.getUser(username)
            return Result.success(apiToDomainUserMapper.mapToDomain(user))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getRepositories(username: String, forceUpdate: Boolean): Result<List<RepositoryEntity>> {
        try {
            val localData = localDataSource.getRepos(username).map(databaseToDomainRepositoryMapper::mapToDomain)
            if (forceUpdate || localData.isEmpty()) {
                val remoteData = loadRemoteRepositories(username)
                return Result.success(remoteData)
            }

            return Result.success(localData)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private suspend fun loadRemoteRepositories(username: String): List<RepositoryEntity> {
        val remoteData = remoteDataSource.getRepos(username).map(apiToDomainRepositoryMapper::mapToDomain)
        localDataSource.saveRepos(remoteData.map(domainToDatabaseRepositoryMapper::mapToDatabase))
        return remoteData
    }
}