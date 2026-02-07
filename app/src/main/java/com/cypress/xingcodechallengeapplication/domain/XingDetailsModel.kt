package com.cypress.xingcodechallengeapplication.domain

data class XingDetailsModel(
    val id: Int,
    val name: String,
    val description: String?,
    val stars: Int,
    val forks: Int,
    val language: String?,
    val ownerLogin: String,
    val ownerAvatarUrl: String?
)
