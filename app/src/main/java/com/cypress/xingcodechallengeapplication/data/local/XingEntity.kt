package com.cypress.xingcodechallengeapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "xing_table")
data class XingEntity(

    @PrimaryKey
    val id: Int,

    val name: String,
    val description: String?,
    val isFork: Boolean,

    val stars: Int,
    val forks: Int,
    val language: String?,

    val ownerLogin: String,
    val ownerAvatarUrl: String?
)