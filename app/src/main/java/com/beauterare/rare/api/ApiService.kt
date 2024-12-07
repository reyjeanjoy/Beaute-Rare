package com.beauterare.rare.api

import com.beauterare.rare.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("signup.php") // Endpoint for the signup functionality
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

    @POST("login.php") // Endpoint for the login functionality
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    // Fetch user profile by email (matches the `getProfile.php` backend logic)
    @GET("getProfile.php")
    fun getUserProfile(
        @Query("email") email: String // Pass email as a query parameter
    ): Call<UserProfileResponse>

    // Fetch all appointments
    @GET("get_appointments.php")
    fun getAppointments(): Call<List<Appointment>>

    // Create a new appointment
    @POST("create_appointment.php")
    fun createAppointment(
        @Body appointment: Appointment
    ): Call<ResponseMessage>
}

