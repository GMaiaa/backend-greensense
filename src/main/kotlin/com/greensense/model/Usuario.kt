package com.greensense.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(unique = true, nullable = false)
    val username: String,

    @Column(nullable = false)
    val senha: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role
)

