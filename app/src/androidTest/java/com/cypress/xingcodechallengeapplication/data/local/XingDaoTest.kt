package com.cypress.xingcodechallengeapplication.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class XingDaoTest {

    private lateinit var xingDataBase: XingDataBase
    private lateinit var xingDao: XingDao

    @Before
    fun setup(){
        xingDataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            XingDataBase::class.java
        ).allowMainThreadQueries().build()
        xingDao = xingDataBase.xingDao
    }

    @Test
    fun insertAndRetrieveItems() = runBlocking {
        val item = XingEntity(
            id = 1,
            "xing_name",
            "repo description",
            false,
            1,
            2,
            "cpp",
            "xing_owner",
            ""
        )

        xingDao.upsert(listOf(item))

        val pagingSource = xingDao.getReposPagingSource()

        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertTrue(loadResult is PagingSource.LoadResult.Page)

        val page = loadResult as PagingSource.LoadResult.Page

        assertTrue(page.data.contains(item))
    }


    @After
    fun tearDown(){
        xingDataBase.close()
    }

}