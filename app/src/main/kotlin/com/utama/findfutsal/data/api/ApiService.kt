package com.utama.findfutsal.data.api

import com.utama.findfutsall.data.model.LoginRequest
import com.utama.findfutsall.data.model.LoginResponse
import com.utama.findfutsall.data.model.RegisterRequest
import com.utama.findfutsall.data.model.RegisterResponse
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