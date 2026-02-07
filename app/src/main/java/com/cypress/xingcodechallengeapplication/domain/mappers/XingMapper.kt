package com.cypress.xingcodechallengeapplication.domain.mappers

import com.cypress.xingcodechallengeapplication.data.local.XingEntity
import com.cypress.xingcodechallengeapplication.domain.XingDetailsModel

fun XingEntity.toXingDetails(): XingDetailsModel {
    return XingDetailsModel(
        id = id,
        name = name,
        description = description,
        stars = stars,
        forks = forks,
        language = language,
        ownerLogin = ownerLogin,
        ownerAvatarUrl = ownerAvatarUrl
    )
}