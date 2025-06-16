// src/main/kotlin/com/greensense/security/dto/AuthResponse.kt
package com.greensense.security.dto

data class AuthResponse(
    val token: String,
    val user: UserResponse
)
