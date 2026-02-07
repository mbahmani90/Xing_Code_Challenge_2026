package com.cypress.xingcodechallengeapplication.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Entity
import com.cypress.xingcodechallengeapplication.data.local.XingDataBase
import com.cypress.xingcodechallengeapplication.data.local.XingEntity
import com.cypress.xingcodechallengeapplication.data.remote.XingClientApi
import com.cypress.xingcodechallengeapplication.data.remote.XingClientApi.Companion.PAGE_SIZE
import com.cypress.xingcodechallengeapplication.data.remote.XingRemoteMediator

interface XingRepository {
    fun getXingPager() : Pager<Int , XingEntity>
}

@OptIn(ExperimentalPagingApi::class)
class XingRepositoryImp(
    private val xingClientApi: XingClientApi,
    private val xingDataBase: XingDataBase
): XingRepository{


    override fun getXingPager(): Pager<Int, XingEntity> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            remoteMediator = XingRemoteMediator(
                xingClientApi , xingDataBase
            ),
            pagingSourceFactory = {
                xingDataBase.xingDao.getReposPagingSource()
            }
        )

    }


}