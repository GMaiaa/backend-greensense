package com.greensense.security.dto

data class RegisterRequest(
    val username: String,
    val senha: String,
    val role: String
)
    