package com.beauterare.rare.models

data class UserProfileResponse(
    val success: Boolean,
    val user: User?
)

data class User(
    val email: String,   // Corresponds to COLUMN_EMAIL
    val password: String, // Corresponds to COLUMN_PASSWORD
    val fname: String,    // First name, to be extracted from COLUMN_NAME
    val lname: String,    // Last name, to be extracted from COLUMN_NAME
    val address: String   // Corresponds to COLUMN_ADDRESS
)
