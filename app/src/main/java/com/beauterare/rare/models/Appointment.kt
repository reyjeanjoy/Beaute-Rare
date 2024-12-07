package com.beauterare.rare.models

data class Appointment(
    val id: Int,
    val customerName: String,
    val makeupName: String,
    val makeupArtist: String,
    val appointmentTime: String,
    val appointmentDate: String
)

