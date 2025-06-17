package com.greensense.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "notificacoes")
data class Notificacao(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,
    val titulo: String,
    val mensagem: String,
    val dataCriacao: Long = System.currentTimeMillis(),
    var lida: Boolean = false
)
