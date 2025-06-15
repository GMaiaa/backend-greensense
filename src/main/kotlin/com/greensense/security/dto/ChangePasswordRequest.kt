package com.greensense.security.dto

data class ChangePasswordRequest(
    val username: String,
    val senhaAtual: String,
    val novaSenha: String
)