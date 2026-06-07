package com.greensense.security.dto

data class UpdateUserRequest(
    val username: String,
    val senha: String,
    val role: String
) 
    
  