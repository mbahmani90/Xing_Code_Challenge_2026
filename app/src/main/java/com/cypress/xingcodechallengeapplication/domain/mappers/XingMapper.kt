package com.cypress.xingcodechallengeapplication.domain.mappers

import com.cypress.xingcodechallengeapplication.data.local.XingEntity
import com.cypress.xingcodechallengeapplication.domain.XingRepoModel

fun XingEntity.toXingModel(): XingRepoModel {

    return XingRepoModel(
        id = id,
        name = name,
        description = description,
        ownerLogin = ownerLogin,
        isFork = isFork,
        ownerAvatarUrl = ownerAvatarUrl
    )

}