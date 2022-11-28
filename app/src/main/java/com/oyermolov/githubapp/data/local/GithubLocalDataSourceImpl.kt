package com.oyermolov.githubapp.data.database

import com.oyermolov.githubapp.data.database.entities.DatabaseRepositoryEntity

class GithubLocalDataSourceImpl(
    private val dao: RepositoriesDao
) : GithubLocalDataSource {
    override suspend fun getRepos(username: String): List<DatabaseRepositoryEntity> {
        return dao.getRepositoriesByOwnerName(username)
    }

    override suspend fun saveRepos(repositories: List<DatabaseRepositoryEntity>) {
        dao.saveAll(repositories)
    }
}