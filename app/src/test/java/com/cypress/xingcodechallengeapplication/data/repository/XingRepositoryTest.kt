package com.cypress.xingcodechallengeapplication.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cypress.xingcodechallengeapplication.data.local.XingDao
import com.cypress.xingcodechallengeapplication.data.local.XingDataBase
import com.cypress.xingcodechallengeapplication.data.local.XingEntity
import com.cypress.xingcodechallengeapplication.data.remote.XingClientApi
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class XingRepositoryTest {

    private val xingClientApi = mockk<XingClientApi>()
    private val xingDao = mockk<XingDao>()
    private val xingDataBase = mockk<XingDataBase>()
    private lateinit var xingRepository: XingRepositoryImp

    @Before
    fun setup() {
        every { xingDataBase.xingDao } returns xingDao
        xingRepository = XingRepositoryImp(xingClientApi, xingDataBase)
    }

    private fun createFakeXingEntity(id: Int) = XingEntity(
        id = id,
        name = "Repo $id",
        description = "Description $id",
        isFork = false,
        stars = id * 10,
        forks = id * 2,
        language = "Kotlin",
        ownerLogin = "user$id",
        ownerAvatarUrl = ""
    )

    @Test
    fun `repository Pager returns expected items`() = runTest {
        val fakeEntities = (1..3).map { createFakeXingEntity(it) }

        val pagingSource = object : PagingSource<Int, XingEntity>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, XingEntity> {
                return LoadResult.Page(
                    data = fakeEntities,
                    prevKey = null,
                    nextKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<Int, XingEntity>): Int? = null
        }

        every { xingDao.getReposPagingSource() } returns pagingSource

        val result = xingDao.getReposPagingSource().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assert(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(fakeEntities, page.data)
    }
}
