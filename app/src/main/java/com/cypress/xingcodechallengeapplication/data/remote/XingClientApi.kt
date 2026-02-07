package com.cypress.xingcodechallengeapplication.data.remote

import com.cypress.xingcodechallengeapplication.BuildConfig
import com.cypress.xingcodechallengeapplication.data.remote.dto.XingDto
import retrofit2.http.GET
import retrofit2.http.Query

interface XingClientApi {

    @GET("orgs/xing/repos")
    suspend fun getRepo(
        @Query("page")page: Int = 1,
        @Query("per_page")pageSize: Int = PAGE_SIZE
    ) : List<XingDto>

    companion object{
        const val BASE_URL = "https://api.github.com/"
        const val GITHUB_TOKEN = BuildConfig.XING_TOKEN
        const val PAGE_SIZE = 20
    }

}