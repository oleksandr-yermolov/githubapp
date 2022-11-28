package com.oyermolov.githubapp.repository.mock

import com.oyermolov.githubapp.data.remote.GithubRemoteDataSource
import com.oyermolov.githubapp.data.remote.entities.ApiRepositoryEntity
import com.oyermolov.githubapp.data.remote.entities.ApiUserEntity
import io.mockk.mockk

class MockGithubRemoteDataSource() :GithubRemoteDataSource {
    override suspend fun getRepos(username: String): List<ApiRepositoryEntity> {
        val remoteUser = ApiUserEntity(
            1L,
            "username",
            null,
            "User Name",
            "Company",
            "Somewhere",
            "Bio"
        )
        return listOf(
            ApiRepositoryEntity(1L,"Remote repo 1", "Description remote repo 1", remoteUser),
            ApiRepositoryEntity(2L,"Remote repo 2", "Description remote repo 2", remoteUser),
        )
    }

    override suspend fun getUser(username: String): ApiUserEntity {
        return ApiUserEntity(1L, "username", mockk(), mockk(), mockk(), mockk(), mockk())
    }
}