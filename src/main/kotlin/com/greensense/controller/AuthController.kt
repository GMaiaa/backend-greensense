package com.greensense.controller

import com.greensense.dto.AuthRequest
import com.greensense.dto.AuthResponse
import com.greensense.dto.RegisterRequest
import com.greensense.security.AuthenticationService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthenticationService
) {

    @PostMapping("/register")
    fun registrar(@RequestBody @Valid request: RegisterRequest): ResponseEntity<AuthResponse> {
        val response = authService.registrar(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/login")
    fun autenticar(@RequestBody @Valid request: AuthRequest): ResponseEntity<AuthResponse> {
        val response = authService.autenticar(request)
        return ResponseEntity.ok(response)
    }
}
