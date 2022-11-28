package com.oyermolov.githubapp.data.mappers

import com.oyermolov.githubapp.data.remote.entities.ApiUserEntity
import com.oyermolov.githubapp.domain.UserEntity

class ApiToDomainUserMapper {
    fun mapToDomain(entity: ApiUserEntity): UserEntity {
        return UserEntity(
            id = entity.id,
            login = entity.login,
            avatarUrl = entity.avatarUrl,
            name = entity.name,
            company = entity.company,
            location = entity.location,
            bio = entity.bio,
            publicRepos = entity.publicRepos,
            followers = entity.followers,
            following = entity.following
        )
    }
}