package com.cypress.xingcodechallengeapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "xing_key_table")
data class XingKeyEntity (
    @PrimaryKey
    val repoId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)