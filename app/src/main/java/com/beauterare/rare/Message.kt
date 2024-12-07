package com.beauterare.rare

data class Message(
    var id: Long = 0, // ID assigned by the database
    val content: String,
    val isSent: Boolean // True if sent, false if received
)
