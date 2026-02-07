package com.cypress.xingcodechallengeapplication.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.cypress.xingcodechallengeapplication.data.local.XingDataBase
import com.cypress.xingcodechallengeapplication.data.remote.XingClientApi
import com.cypress.xingcodechallengeapplication.data.remote.XingClientApi.Companion.BASE_URL
import com.cypress.xingcodechallengeapplication.data.remote.XingClientApi.Companion.GITHUB_TOKEN
import com.cypress.xingcodechallengeapplication.data.repository.XingRepository
import com.cypress.xingcodechallengeapplication.data.repository.XingRepositoryImp
import com.cypress.xingcodechallengeapplication.presentation.viewModel.XingDetailsViewModel
import com.cypress.xingcodechallengeapplication.presentation.viewModel.XingViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit


val platformModule = module {

    single {
        Json { ignoreUnknownKeys = true }
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            XingDataBase::class.java,
            "xingDb.db"
        ).build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "token $GITHUB_TOKEN")
                    .addHeader("Accept", "application/vnd.github+json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        val json: Json = get()
        val client : OkHttpClient = get()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(XingClientApi::class.java)
    }

    factoryOf(::XingRepositoryImp).bind<XingRepository>()
    viewModelOf(::XingViewModel)
    viewModelOf(::XingDetailsViewModel)
}