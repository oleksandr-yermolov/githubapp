package com.oyermolov.githubapp.data.mappers

import com.oyermolov.githubapp.data.local.database.entities.DatabaseRepositoryEntity
import com.oyermolov.githubapp.domain.RepositoryEntity

class DomainToDatabaseRepositoryMapper {
    fun mapToDatabase(entity: RepositoryEntity) : DatabaseRepositoryEntity {
        return DatabaseRepositoryEntity(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            ownerName = entity.ownerName
        )
    }
}