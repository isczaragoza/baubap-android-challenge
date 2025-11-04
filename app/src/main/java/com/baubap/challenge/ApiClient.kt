package com.baubap.challenge

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ApiClient {
    private const val BASE_URL = "https://reqres.in/"
    private const val API_KEY = "reqres-free-v1"

    private val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("x-api-key", API_KEY)
            .build()
        chain.proceed(request)
    }.build()
    private val networkJson: Json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}