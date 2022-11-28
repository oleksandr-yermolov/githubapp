package com.oyermolov.githubapp.mappers

import com.oyermolov.githubapp.data.remote.entities.ApiRepositoryEntity
import com.oyermolov.githubapp.data.mappers.ApiToDomainRepositoryMapper
import com.oyermolov.githubapp.data.remote.entities.ApiUserEntity
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.junit.Test

class ApiToDomainRepositoryMapperTest {
    private lateinit var apiRepositoryEntity: ApiRepositoryEntity
    private lateinit var apiToDomainRepositoryMapper: ApiToDomainRepositoryMapper

    @Before
    fun setup() {
        apiToDomainRepositoryMapper = ApiToDomainRepositoryMapper()
        apiRepositoryEntity = ApiRepositoryEntity(
            1L,
            "RepoName",
            "RepoDesc",
            ApiUserEntity(
                1L,
                "username",
                null,
                "User Name",
                "Company",
                "Somewhere",
                "Bio"
            )
        )
    }

    @Test
    fun mapApiRepoToDomainTest() {
        val repository = apiToDomainRepositoryMapper.mapToDomain(apiRepositoryEntity)
        assertThat(repository.name).isEqualTo(apiRepositoryEntity.name)
        assertThat(repository.description).isEqualTo(apiRepositoryEntity.description)
        assertThat(repository.ownerName).isEqualTo(apiRepositoryEntity.owner.login)
    }
}