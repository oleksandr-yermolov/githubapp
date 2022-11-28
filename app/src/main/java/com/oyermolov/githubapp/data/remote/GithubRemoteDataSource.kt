package com.oyermolov.githubapp.data.remote

import com.oyermolov.githubapp.data.remote.entities.ApiRepositoryEntity
import com.oyermolov.githubapp.data.remote.entities.ApiUserEntity

interface GithubRemoteDataSource {
    suspend fun getRepos(username: String) : List<ApiRepositoryEntity>
    suspend fun getUser(username: String) : ApiUserEntity
}
