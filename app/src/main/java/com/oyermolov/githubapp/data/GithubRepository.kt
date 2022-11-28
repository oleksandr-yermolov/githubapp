package com.oyermolov.githubapp.data

import com.oyermolov.githubapp.domain.RepositoryEntity
import com.oyermolov.githubapp.domain.UserEntity

interface GithubRepository {
    suspend fun getUser(username: String): Result<UserEntity>
    suspend fun getRepositories(username: String, forceUpdate: Boolean): Result<List<RepositoryEntity>>
}