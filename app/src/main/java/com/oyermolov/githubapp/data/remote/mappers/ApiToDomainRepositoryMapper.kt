package com.oyermolov.githubapp.data.mappers

import com.oyermolov.githubapp.data.remote.entities.ApiRepositoryEntity
import com.oyermolov.githubapp.domain.RepositoryEntity

class ApiToDomainRepositoryMapper {
    fun mapToDomain(entity: ApiRepositoryEntity) : RepositoryEntity {
        return RepositoryEntity(
            id = entity.id,
            name = entity.name,
            description = entity.description.orEmpty(),
            ownerName = entity.owner.login
        )
    }
}