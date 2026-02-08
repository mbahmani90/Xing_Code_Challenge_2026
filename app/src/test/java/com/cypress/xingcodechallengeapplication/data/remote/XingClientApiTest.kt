package com.cypress.xingcodechallengeapplication.data.remote

import androidx.paging.ExperimentalPagingApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import java.io.File

@RunWith(RobolectricTestRunner::class)
class XingClientApiTest {

    private lateinit var mockWebServer : MockWebServer
    private lateinit var xingClientApi: XingClientApi

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val json = Json {
            ignoreUnknownKeys = true
        }
        xingClientApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(XingClientApi::class.java)


    }

    @Test
    fun `request for xing repo list`() = runTest {

        val jsonString = File("src/test/resources/XingFakeResponse.json").readText()

        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(jsonString)
        )

        val xingDto = xingClientApi.getRepo(1 , 20)

        TestCase.assertEquals(2 , xingDto.size)
        TestCase.assertEquals(240669 , xingDto[1].id)

    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

}