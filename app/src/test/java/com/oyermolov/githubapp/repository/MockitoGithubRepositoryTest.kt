package com.oyermolov.githubapp.repository

import com.oyermolov.githubapp.data.GithubRepository
import com.oyermolov.githubapp.data.GithubRepositoryImpl
import com.oyermolov.githubapp.data.database.GithubLocalDataSource
import com.oyermolov.githubapp.data.database.entities.DatabaseRepositoryEntity
import com.oyermolov.githubapp.data.mappers.ApiToDomainRepositoryMapper
import com.oyermolov.githubapp.data.mappers.ApiToDomainUserMapper
import com.oyermolov.githubapp.data.mappers.DatabaseToDomainRepositoryMapper
import com.oyermolov.githubapp.data.mappers.DomainToDatabaseRepositoryMapper
import com.oyermolov.githubapp.data.remote.GithubRemoteDataSource
import com.oyermolov.githubapp.data.remote.entities.ApiRepositoryEntity
import com.oyermolov.githubapp.data.remote.entities.ApiUserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MockitoGithubRepositoryTest {

    private lateinit var githubRepository: GithubRepository

    @Mock
    private lateinit var localDataSource: GithubLocalDataSource

    @Mock
    private lateinit var remoteDataSource: GithubRemoteDataSource

    private val remoteUser = ApiUserEntity(
        1L,
        "username",
        null,
        "User Name",
        "Company",
        "Somewhere",
        "Bio"
    )

    private val remoteRepos = listOf(
        ApiRepositoryEntity(1L,"Remote repo 1", "Description remote repo 1", remoteUser),
        ApiRepositoryEntity(2L,"Remote repo 2", "Description remote repo 2", remoteUser),
    )

    private val localRepos = listOf(
        DatabaseRepositoryEntity(1L,"Local repo 1", "Local repo 1 description", "username"),
        DatabaseRepositoryEntity(2L,"Local repo 2", "Local repo 2 description", "username"),
    )

    @Before
    fun setup() {
        githubRepository = GithubRepositoryImpl(
            remoteDataSource,
            localDataSource,
            ApiToDomainRepositoryMapper(),
            DatabaseToDomainRepositoryMapper(),
            DomainToDatabaseRepositoryMapper(),
            ApiToDomainUserMapper()
        )
    }

    @Test
    fun testForceRepositoriesLoading() = runTest {
        val username = "testUser"
        val expectedRepoName = "Remote repo 1"

        `when`(localDataSource.getRepos(username)).thenReturn(emptyList())
        `when`(remoteDataSource.getRepos(username)).thenReturn(remoteRepos)

        val result = githubRepository.getRepositories(username, forceUpdate = true)

        verify(remoteDataSource, times(1)).getRepos(username)
        verify(localDataSource, times(1)).getRepos(username)
        assert(result.isSuccess)

        val repositories = result.getOrThrow()
        assert(repositories.isNotEmpty())

        assertEquals(repositories.first().name, expectedRepoName)
    }

    @Test
    fun testRepositoriesCaching() = runTest {
        val username = "testUser"
        val expectedRepoName = "Local repo 1"

        `when`(localDataSource.getRepos(username)).thenReturn(localRepos)

        val result = githubRepository.getRepositories(username, forceUpdate = false)

        verify(remoteDataSource, never()).getRepos(anyString())
        verify(localDataSource, times(1)).getRepos(username)
        assert(result.isSuccess)

        val repositories = result.getOrThrow()
        assert(repositories.isNotEmpty())

        assertEquals(repositories.first().name, expectedRepoName)
    }
}