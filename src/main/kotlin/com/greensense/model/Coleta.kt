package com.greensense.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class Coleta(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false)
    val lixeiraId: UUID,

    @Column(nullable = false)
    val dataHora: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val quantidadeColetada: Int,

    @Column(nullable = false)
    val responsavel: String,

    @Column(nullable = false)
    val metodo: String // Ex: "manual", "sensor"
)
