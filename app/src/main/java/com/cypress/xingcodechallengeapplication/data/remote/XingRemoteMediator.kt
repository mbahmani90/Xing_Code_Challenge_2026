package com.cypress.xingcodechallengeapplication.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.cypress.xingcodechallengeapplication.data.local.XingDataBase
import com.cypress.xingcodechallengeapplication.data.local.XingEntity
import com.cypress.xingcodechallengeapplication.data.local.XingKeyEntity
import com.cypress.xingcodechallengeapplication.data.mapper.toEntity
import com.cypress.xingcodechallengeapplication.data.remote.XingClientApi.Companion.PAGE_SIZE

@OptIn(ExperimentalPagingApi::class)
class XingRemoteMediator(

    private val xingClientApi: XingClientApi,
    private val xingDataBase: XingDataBase

) : RemoteMediator<Int , XingEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, XingEntity>
    ): MediatorResult {

        val nextKey = when(loadType){
            LoadType.REFRESH -> {
               1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                val xingKeyEntity = xingDataBase.xingKeyDao.getPage(lastItem.id)

                xingKeyEntity.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)

            }
        }

        val result = xingClientApi.getRepo(nextKey , PAGE_SIZE)

        xingDataBase.withTransaction {

            if(loadType == LoadType.REFRESH){
                xingDataBase.xingDao.clearAll()
                xingDataBase.xingKeyDao.clearAll()
            }

            xingDataBase.xingDao.upsert(result.map { it.toEntity() })

            xingDataBase.xingKeyDao.upsert(
                result.map {
                    XingKeyEntity(
                        repoId = it.id,
                        prevKey = null,
                        nextKey = nextKey + 1
                    )
                }
            )

        }

        return MediatorResult.Success(endOfPaginationReached = result.isEmpty())

    }


}