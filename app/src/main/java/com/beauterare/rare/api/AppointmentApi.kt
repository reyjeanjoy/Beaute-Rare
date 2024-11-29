package com.beauterare.rare.api

import com.beauterare.rare.models.Appointment
import com.beauterare.rare.models.ResponseMessage
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AppointmentApi {
    @GET("get_appointments.php")
    suspend fun getAppointments(): Response<List<Appointment>>

    @FormUrlEncoded
    @POST("create_appointment.php")
    suspend fun createAppointment(
        @Field("customerName") customerName: String,
        @Field("makeupName") makeupName: String,
        @Field("makeupArtist") makeupArtist: String,
        @Field("appointmentTime") appointmentTime: String,
        @Field("appointmentDate") appointmentDate: String
    ): Response<ResponseMessage>

    @FormUrlEncoded
    @POST("delete_appointment.php")
    suspend fun deleteAppointment(@Field("id") id: Int): Response<ResponseMessage>
}