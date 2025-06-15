package com.greensense.security

import com.greensense.security.dto.AuthRequest
import com.greensense.security.dto.AuthResponse
import com.greensense.security.dto.RegisterRequest
import com.greensense.model.Role
import com.greensense.model.Usuario
import com.greensense.repository.UsuarioRepository
import com.greensense.security.dto.ChangePasswordRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthenticationService(
    private val repository: UsuarioRepository,
    private val encoder: PasswordEncoder,
    private val jwtService: JWTService,
    private val authManager: AuthenticationManager
) {
    fun registrar(request: RegisterRequest): AuthResponse {
        val user = Usuario(
            username = request.username,
            senha = encoder.encode(request.senha),
            role = Role.valueOf("ROLE_${request.role.uppercase()}")
        )
        repository.save(user)
        val token = jwtService.generateToken(user)
        return AuthResponse(token)
    }

    fun autenticar(request: AuthRequest): AuthResponse {
        val auth = UsernamePasswordAuthenticationToken(request.username, request.senha)
        authManager.authenticate(auth)

        val user = repository.findByUsername(request.username)!!
        val token = jwtService.generateToken(user)
        return AuthResponse(token)
    }

    fun changePassword(request: ChangePasswordRequest) {
        val user = repository.findByUsername(request.username)
            ?: throw Exception("Usuário não encontrado")
        if (!encoder.matches(request.senhaAtual, user.senha)) {
            throw Exception("Senha atual incorreta")
        }
        val updatedUser = user.copy(senha = encoder.encode(request.novaSenha))
        repository.save(updatedUser)
    }
}
