package com.cypress.xingcodechallengeapplication.data.mapper

import com.cypress.xingcodechallengeapplication.data.local.XingEntity
import com.cypress.xingcodechallengeapplication.data.remote.dto.XingDto


fun XingDto.toEntity() : XingEntity{
    return XingEntity(
        id = id,

        name = name,
        description = description,
        isFork = fork,

        stars = stargazers_count,
        forks = forks,
        language = language,

        ownerLogin = owner.login,
        ownerAvatarUrl = owner.avatar_url
    )
}