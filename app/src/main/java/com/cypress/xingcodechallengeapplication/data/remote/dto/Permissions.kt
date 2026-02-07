package com.cypress.xingcodechallengeapplication.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Permissions(
    val admin: Boolean,
    val maintain: Boolean,
    val pull: Boolean,
    val push: Boolean,
    val triage: Boolean
)