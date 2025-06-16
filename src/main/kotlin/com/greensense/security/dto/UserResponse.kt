// src/main/kotlin/com/greensense/security/dto/UserResponse.kt
package com.greensense.security.dto

data class UserResponse(
    val id: String,
    val username: String,
    val role: String
)
