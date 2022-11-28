package com.oyermolov.githubapp.data.mappers

import com.oyermolov.githubapp.data.local.database.entities.DatabaseRepositoryEntity
import com.oyermolov.githubapp.domain.RepositoryEntity

class DatabaseToDomainRepositoryMapper {
    fun mapToDomain(entity: DatabaseRepositoryEntity) : RepositoryEntity {
        return RepositoryEntity(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            ownerName = entity.ownerName
        )
    }
}