package com.cypress.xingcodechallengeapplication.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface XingDao {

    @Upsert
    suspend fun upsert(list: List<XingEntity>)

    @Query("SELECT * FROM xing_table ORDER BY name ASC")
    fun getReposPagingSource() : PagingSource<Int , XingEntity>

    @Query("DELETE FROM xing_table")
    suspend fun clearAll()

}