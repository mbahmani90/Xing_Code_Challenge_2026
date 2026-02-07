package com.cypress.xingcodechallengeapplication.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface XingDao {

    @Upsert
    suspend fun upsert(list: List<XingEntity>)

    @Query("SELECT * FROM xing_table ORDER BY id ASC")
    fun getReposPagingSource() : PagingSource<Int , XingEntity>

    @Query("DELETE FROM xing_table")
    suspend fun clearAll()

    @Query("SELECT * FROM xing_table WHERE id = :id")
    fun getById(id: Long): Flow<XingEntity>

}