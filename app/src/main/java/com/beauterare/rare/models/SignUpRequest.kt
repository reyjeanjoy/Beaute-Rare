package com.beauterare.rare.models

data class SignUpRequest(
    val fname: String,
    val lname: String,
    val email: String,
    val password: String,
    val address: String
)
