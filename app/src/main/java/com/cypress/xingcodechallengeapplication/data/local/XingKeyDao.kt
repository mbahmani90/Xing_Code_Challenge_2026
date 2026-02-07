package com.cypress.xingcodechallengeapplication.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface XingKeyDao {

    @Upsert
    suspend fun upsert(list : List<XingKeyEntity>)

    @Query("SELECT * FROM xing_key_table WHERE repoId = :repoId")
    suspend fun getPage(repoId : Int) : XingKeyEntity

    @Query("DELETE FROM xing_key_table")
    suspend fun clearAll()

}