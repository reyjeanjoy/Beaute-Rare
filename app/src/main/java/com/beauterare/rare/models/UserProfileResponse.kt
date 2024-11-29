package com.beauterare.rare.models

data class UserProfileResponse(
    val success: Boolean,
    val user: User?
)

data class User(
    val fname: String,
    val lname: String,
    val email: String,
    val address: String
)