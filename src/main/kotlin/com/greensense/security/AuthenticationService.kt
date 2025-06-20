package com.greensense.security

import com.greensense.security.dto.AuthRequest
import com.greensense.security.dto.AuthResponse
import com.greensense.security.dto.RegisterRequest
import com.greensense.security.dto.UserResponse
import com.greensense.model.Role
import com.greensense.model.Usuario
import com.greensense.repository.UsuarioRepository
import com.greensense.security.dto.UpdateUserRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthenticationService(
    private val repository: UsuarioRepository,
    private val encoder: PasswordEncoder,
    private val jwtService: JWTService,
    private val authManager: AuthenticationManager,
    private val usuarioRepository: UsuarioRepository
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
    fun atualizarUsuario(id: UUID, request: UpdateUserRequest): UserResponse {
        val usuario = repository.findById(id)
            .orElseThrow { IllegalArgumentException("Usuário não encontrado") }

        val novoRole = when (request.role.uppercase()) {
            "ROLE_ADMIN" -> Role.ROLE_ADMIN
            "ROLE_OPERACIONAL" -> Role.ROLE_OPERACIONAL
            else -> throw IllegalArgumentException("Role inválido: ${request.role}")
        }

        val usuarioAtualizado = usuario.copy(
            username = request.username,
            role = novoRole
        )
        repository.save(usuarioAtualizado)

        return UserResponse(
            id = usuarioAtualizado.id.toString(),
            username = usuarioAtualizado.username,
            role = usuarioAtualizado.role.name
        )
    }
    fun deletarUsuario(id: UUID) {
        usuarioRepository.deleteById(id)
    }

}
