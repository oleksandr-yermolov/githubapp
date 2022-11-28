package com.oyermolov.githubapp.repository

import com.oyermolov.githubapp.data.GithubRepository
import com.oyermolov.githubapp.data.GithubRepositoryImpl
import com.oyermolov.githubapp.data.mappers.ApiToDomainRepositoryMapper
import com.oyermolov.githubapp.data.mappers.ApiToDomainUserMapper
import com.oyermolov.githubapp.data.mappers.DatabaseToDomainRepositoryMapper
import com.oyermolov.githubapp.data.mappers.DomainToDatabaseRepositoryMapper
import com.oyermolov.githubapp.repository.mock.MockGithubLocalDataSource
import com.oyermolov.githubapp.repository.mock.MockGithubRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest

class GithubRepositoryTest {
    private lateinit var githubRepository: GithubRepository

    @Before
    fun setup() {
        githubRepository = GithubRepositoryImpl(
            MockGithubRemoteDataSource(),
            MockGithubLocalDataSource(),
            ApiToDomainRepositoryMapper(),
            DatabaseToDomainRepositoryMapper(),
            DomainToDatabaseRepositoryMapper(),
            ApiToDomainUserMapper()
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testForceRepositoriesLoading() = runTest {
        val username = "testUser"
        val expectedRepoName = "Remote repo 1"

        val result = githubRepository.getRepositories(username, forceUpdate = true)
        assert(result.isSuccess)

        val repositories = result.getOrThrow()
        assert(repositories.isNotEmpty())

        assertEquals(repositories.first().name, expectedRepoName)
    }
}