package com.greensense.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class MedicaoLixeira(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val idLixeira: UUID,
    val distancia: Double,
    val dataHora: LocalDateTime = LocalDateTime.now()
)
