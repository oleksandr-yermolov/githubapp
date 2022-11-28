package com.oyermolov.githubapp.repository.mock

import com.oyermolov.githubapp.data.database.GithubLocalDataSource
import com.oyermolov.githubapp.data.database.entities.DatabaseRepositoryEntity

class MockGithubLocalDataSource() : GithubLocalDataSource {
    override suspend fun getRepos(username: String): List<DatabaseRepositoryEntity> {
        return listOf(
            DatabaseRepositoryEntity(1L, "Local repo 1", "Local repo 1 description", "username"),
            DatabaseRepositoryEntity(2L,"Local repo 2", "Local repo 2 description", "username"),
        )
    }

    override suspend fun saveRepos(repositories: List<DatabaseRepositoryEntity>) {
        //TODO: Implement
    }
}