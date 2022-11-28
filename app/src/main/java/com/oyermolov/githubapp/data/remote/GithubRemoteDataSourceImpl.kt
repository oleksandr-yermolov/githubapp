package com.oyermolov.githubapp.data.remote

import com.oyermolov.githubapp.data.remote.entities.ApiRepositoryEntity
import com.oyermolov.githubapp.data.remote.entities.ApiUserEntity

class GithubRemoteDataSourceImpl(
    private val api: GithubApi,
): GithubRemoteDataSource {

    override suspend fun getRepos(username: String): List<ApiRepositoryEntity> {
        return api.getRepos(username)
    }

    override suspend fun getUser(username: String): ApiUserEntity {
        return api.getUser(username)
    }
}