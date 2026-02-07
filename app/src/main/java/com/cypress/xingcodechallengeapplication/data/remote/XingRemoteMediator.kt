package com.cypress.xingcodechallengeapplication.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.cypress.xingcodechallengeapplication.data.local.XingDataBase
import com.cypress.xingcodechallengeapplication.data.local.XingEntity
import com.cypress.xingcodechallengeapplication.data.local.XingKeyEntity
import com.cypress.xingcodechallengeapplication.data.mapper.toEntity
import com.cypress.xingcodechallengeapplication.data.remote.XingClientApi.Companion.PAGE_SIZE
import kotlinx.coroutines.delay
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class XingRemoteMediator(

    private val xingClientApi: XingClientApi,
    private val xingDataBase: XingDataBase

) : RemoteMediator<Int , XingEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, XingEntity>
    ): MediatorResult {

        try {
            val nextKey = when(loadType){
                LoadType.REFRESH -> {
                    1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val remoteKeys = xingDataBase.xingKeyDao.getLastRemoteKey()
                    val tempKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    tempKey
                }
            }

            val result = xingClientApi.getRepo(nextKey , PAGE_SIZE)

            xingDataBase.withTransaction {

                if(loadType == LoadType.REFRESH){
                    xingDataBase.xingDao.clearAll()
                    xingDataBase.xingKeyDao.clearAll()
                }

                xingDataBase.xingDao.upsert(result.map { it.toEntity() })
                val nextKeyValue = if (result.size < PAGE_SIZE) null else nextKey + 1

                xingDataBase.xingKeyDao.upsert(
                    result.map {
                        XingKeyEntity(
                            repoId = it.id,
                            prevKey = null,
                            nextKey = nextKeyValue
                        )
                    }
                )
            }

            return MediatorResult.Success(endOfPaginationReached = (result.size < PAGE_SIZE))
        }catch (e: HttpException){
            return MediatorResult.Error(e)
        }catch (e: IOException){
            return MediatorResult.Error(e)
        }

    }


}