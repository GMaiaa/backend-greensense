package com.greensense.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class Lixeira(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false)
    val tipo: String, 

    @Column(nullable = false)
    val endereco: String,

    @Column(nullable = false)
    val capacidadeMaxima: Int,

    @Column(nullable = false)
    var nivelAtual: Int = 0,

    @Column(nullable = false)
    var statusSensor: Boolean = false, 

    var sensorId: String? = null, 

    @Column(nullable = false)
    val dataCadastro: LocalDateTime = LocalDateTime.now()
)
