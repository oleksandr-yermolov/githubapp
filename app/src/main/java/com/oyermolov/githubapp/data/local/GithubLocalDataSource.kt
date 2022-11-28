package com.oyermolov.githubapp.data.database

import com.oyermolov.githubapp.data.database.entities.DatabaseRepositoryEntity

interface GithubLocalDataSource {
    suspend fun getRepos(username: String) : List<DatabaseRepositoryEntity>
    suspend fun saveRepos(repositories: List<DatabaseRepositoryEntity>)
}