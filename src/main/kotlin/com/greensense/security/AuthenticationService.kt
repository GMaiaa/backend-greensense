package com.greensense.security

import com.greensense.security.dto.AuthRequest
import com.greensense.security.dto.AuthResponse
import com.greensense.security.dto.RegisterRequest
import com.greensense.security.dto.UserResponse
import com.greensense.model.Role
import com.greensense.model.Usuario
import com.greensense.repository.UsuarioRepository
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
        val role = when (request.role.lowercase()) {
            "operacional" -> Role.ROLE_OPERACIONAL
            "admin" -> Role.ROLE_ADMIN
            else -> throw IllegalArgumentException("Role inválido: ${request.role}")
        }
    
        val user = Usuario(
            username = request.username,
            senha = encoder.encode(request.senha),
            role = role
        )
        repository.save(user)
    
        val token = jwtService.generateToken(user)
    
        return AuthResponse(
            token = token,
            user = UserResponse(
                id = user.id.toString(),
                username = user.username,
                role = user.role.name
            )
        )
    }
    

    fun autenticar(request: AuthRequest): AuthResponse {
        val auth = UsernamePasswordAuthenticationToken(request.username, request.senha)
        authManager.authenticate(auth)

        val user = repository.findByUsername(request.username)!!

        val token = jwtService.generateToken(user)

        return AuthResponse(
            token = token,
            user = UserResponse(
                id = user.id.toString(),
                username = user.username,
                role = user.role.name
            )
        )
    }

 fun listarUsuarios(): List<UserResponse> {
    return repository.findAll().map {
        UserResponse(
            id = it.id.toString(),
            username = it.username,
            role = it.role.name
        )
    }
}

}
