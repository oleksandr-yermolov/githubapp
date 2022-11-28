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
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MockkGithubRepositoryTest {
    private lateinit var githubRepository: GithubRepository
    private lateinit var localDataSource: GithubLocalDataSource
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
        ApiRepositoryEntity(2L,"Remote repo 2", "Description remote repo 2", remoteUser)
    )

    private val localRepos = listOf(
        DatabaseRepositoryEntity(1L,"Local repo 1", "Local repo 1 description", "username"),
        DatabaseRepositoryEntity(2L,"Local repo 2", "Local repo 2 description", "username")
    )

    @Before
    fun setup() {
        localDataSource = mockk(relaxed = true)
        remoteDataSource = mockk()

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

        coEvery { localDataSource.getRepos(username) } returns emptyList()
        coEvery { remoteDataSource.getRepos(username) } returns remoteRepos

        val result = githubRepository.getRepositories(username, forceUpdate = true)

        coVerify { localDataSource.getRepos(username) }
        coVerify { remoteDataSource.getRepos(username) }
        assert(result.isSuccess)

        val repositories = result.getOrThrow()
        assert(repositories.isNotEmpty())

        assertEquals(repositories.first().name, expectedRepoName)
    }

    @Test
    fun testRepositoriesCaching() = runTest {
        val username = "testUser"
        val expectedRepoName = "Local repo 1"

        coEvery { localDataSource.getRepos(username) } returns localRepos
        coEvery { remoteDataSource.getRepos(username) } returns remoteRepos

        val result = githubRepository.getRepositories(username, forceUpdate = false)

        coVerify(exactly = 1) { localDataSource.getRepos(username) }
        coVerify(inverse = true) { remoteDataSource.getRepos(username) }
        assert(result.isSuccess)

        val repositories = result.getOrThrow()
        assert(repositories.isNotEmpty())

        assertEquals(repositories.first().name, expectedRepoName)
    }

    @Test
    fun testUserFollowersAndFollowing() = runTest {
        val username = "testUser"

        coEvery { remoteDataSource.getUser(username) } returns remoteUser

        val result = githubRepository.getUser(username)

        coVerify(exactly = 1) { remoteDataSource.getUser(username) }
        assert(result.isSuccess)

        val user = result.getOrThrow()
        assert(user.followers >= 0)
        assert(user.following >= 0)
    }


    @Test
    fun testUserPublicRepos() = runTest {
        val username = "testUser"

        coEvery { remoteDataSource.getUser(username) } returns remoteUser

        val result = githubRepository.getUser(username)

        coVerify(exactly = 1) { remoteDataSource.getUser(username) }
        assert(result.isSuccess)

        val user = result.getOrThrow()
        assert(user.publicRepos >= 0)
    }
}