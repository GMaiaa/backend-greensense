package com.greensense.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Revisao(
    @Id
    @GeneratedValue
    val id: Long = 0,

    val descricao: String,

    var status: String = "ABERTO",

    val nomeUsuario: String,

    @ManyToOne
    val lixeira: Lixeira,

    val dataSolicitacao: LocalDateTime = LocalDateTime.now()
)
