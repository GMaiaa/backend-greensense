package com.greensense.controller

import com.greensense.security.dto.AuthRequest
import com.greensense.security.dto.AuthResponse
import com.greensense.security.dto.RegisterRequest
import com.greensense.security.AuthenticationService
import com.greensense.security.dto.ChangePasswordRequest
import com.greensense.model.Usuario
import com.greensense.repository.UsuarioRepository
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthenticationService,
    private val usuarioRepository: UsuarioRepository // injete o repository
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

    @PostMapping("/change-password")
    fun changePassword(@RequestBody @Valid request: ChangePasswordRequest): ResponseEntity<Void> {
        authService.changePassword(request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/usuarios")
    fun listarUsuarios(): List<Usuario> = usuarioRepository.findAll()

    @DeleteMapping("/usuarios/{id}")
    fun deletarUsuario(@PathVariable id: UUID): ResponseEntity<Void> {
        return if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
    @PutMapping("/usuarios/{id}")
    fun editarUsuario(@PathVariable id: UUID, @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val existente = usuarioRepository.findById(id)
        return if (existente.isPresent) {
            val atualizado = existente.get().copy(
                username = usuario.username,
                role = usuario.role
                // NÃO altere a senha aqui por segurança, a menos que queira permitir!
            )
            ResponseEntity.ok(usuarioRepository.save(atualizado))
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
