package com.beauterare.rare.api

import com.beauterare.rare.models.Appointment  // Import the Appointment class
import com.beauterare.rare.models.ResponseMessage
import com.beauterare.rare.models.LoginRequest
import com.beauterare.rare.models.LoginResponse
import com.beauterare.rare.models.SignUpRequest
import com.beauterare.rare.models.SignUpResponse
import com.beauterare.rare.models.UserProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("signup.php") // Endpoint for the signup functionality
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

    @POST("login.php") // Endpoint for the login functionality
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("api/getCurrentUserEmail") // Adjust the endpoint path to match your backend
    fun getCurrentUserEmail(): Call<UserProfileResponse>

    // Fetch all appointments
    @GET("get_appointments.php")
    fun getAppointments(): Call<List<Appointment>>  // Use the correct Appointment class here

    // Create a new appointment
    @FormUrlEncoded
    @POST("create_appointment.php")
    fun createAppointment(
        @Field("customerName") customerName: String,
        @Field("makeupName") makeupName: String,
        @Field("makeupArtist") makeupArtist: String,
        @Field("appointmentTime") appointmentTime: String,
        @Field("appointmentDate") appointmentDate: String
    ): Call<ResponseMessage>
}