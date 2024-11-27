package com.beauterare.rare.api

import com.beauterare.rare.models.LoginRequest
import com.beauterare.rare.models.LoginResponse
import com.beauterare.rare.models.SignUpRequest
import com.beauterare.rare.models.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("signup.php") // Endpoint for the signup functionality
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

    @POST("login.php") // Endpoint for the login functionality
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}
