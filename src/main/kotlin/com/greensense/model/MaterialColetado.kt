package com.greensense.model

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class MaterialColetado(
    @Id
    @GeneratedValue
    val id: Long = 0,

    val tipo: String,

    val quantidade: Double,

    val unidade: String,

    val nomeUsuario: String,

    @ManyToOne
    val lixeira: Lixeira,

    val dataRegistro: LocalDateTime = LocalDateTime.now()
)
