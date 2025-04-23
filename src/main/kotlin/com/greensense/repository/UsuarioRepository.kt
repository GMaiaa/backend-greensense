package com.greensense.repository

import com.greensense.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UsuarioRepository : JpaRepository<Usuario, UUID> {
    fun findByUsername(username: String): Usuario?
}
