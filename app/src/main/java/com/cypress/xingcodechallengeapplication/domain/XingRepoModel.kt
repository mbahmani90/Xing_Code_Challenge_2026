package com.cypress.xingcodechallengeapplication.domain

data class XingRepoModel(
    val id: Int,
    val name: String,
    val description: String?,
    val ownerLogin: String,
    val isFork: Boolean
)
