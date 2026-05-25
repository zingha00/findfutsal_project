package com.utama.findfutsal.data.api

import com.utama.findfutsal.data.model.LoginRequest
import com.utama.findfutsal.data.model.LoginResponse
import com.utama.findfutsal.data.model.RegisterRequest
import com.utama.findfutsal.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login.php")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("register.php")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("forgot_password.php")
    suspend fun forgotPassword(
        @Body request: Map<String, String>
    ): Response<Map<String, Any>>
}