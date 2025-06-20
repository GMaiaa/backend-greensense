package com.greensense.controller

import com.greensense.security.dto.AuthRequest
import com.greensense.security.dto.AuthResponse
import com.greensense.security.dto.RegisterRequest
import com.greensense.security.dto.UserResponse
import com.greensense.security.AuthenticationService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import com.greensense.security.dto.UpdateUserRequest
import java.util.UUID

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthenticationService
) {

    // 🔐 Registro de novo usuário
    @PostMapping("/register")
    fun registrar(@RequestBody @Valid request: RegisterRequest): ResponseEntity<AuthResponse> {
        val response = authService.registrar(request)
        return ResponseEntity.ok(response)
    }

    // 🔐 Login
    @PostMapping("/login")
    fun autenticar(@RequestBody @Valid request: AuthRequest): ResponseEntity<AuthResponse> {
        val response = authService.autenticar(request)
        return ResponseEntity.ok(response)
    }

    // 🔐 Listar todos os usuários - Acesso apenas para ADMIN
    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun listarUsuarios(): ResponseEntity<List<UserResponse>> {
        val usuarios = authService.listarUsuarios()
        return ResponseEntity.ok(usuarios)
    }

    @PutMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun atualizarUsuario(
        @PathVariable id: UUID,
        @RequestBody @Valid request: UpdateUserRequest
    ): ResponseEntity<UserResponse> {
        val usuarioAtualizado = authService.atualizarUsuario(id, request)
        return ResponseEntity.ok(usuarioAtualizado)
    }

    @DeleteMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun deletarUsuario(@PathVariable id: UUID): ResponseEntity<Void> {
        authService.deletarUsuario(id)
        return ResponseEntity.noContent().build()
    }
}
