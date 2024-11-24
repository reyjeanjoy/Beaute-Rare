package com.beauterare.rare

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // Login endpoint
    @POST("login.php")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("register.php")
    fun registerUser(@Body signUpRequest: SignUpRequest): Call<SignUpResponse>


    // Get Users endpoint
    @GET("get_data.php") // Make sure this path matches your server setup
    fun getUsers(): Call<List<User>>
}

// Login request data class
data class LoginRequest(
    val username: String,
    val password: String
)

// Login response data class
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: User?
)
data class SignUpRequest(
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val address: String
)
data class SignUpResponse(
    val success: Boolean,
    val message: String
)

