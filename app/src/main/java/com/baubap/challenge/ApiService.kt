package com.baubap.challenge

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET

interface ApiService {
    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    /**
     * @POST("api/register")
     * suspend fun register(@Header("Authorization") apiKey: String,
     * @Body request: RegisterRequest): Response<RegisterResponse>
     *
     * @POST("api/login")
     * suspend fun login(@Header("Authorization") apiKey: String,
     * @Body request: LoginRequest): Response<LoginResponse>
     *
     *
     * **/
}