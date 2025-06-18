package com.greensense.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class Notificacao(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val titulo: String,

    @Column(nullable = false)
    val mensagem: String,

    @Column(nullable = false)
    val tipo: String,

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    val destinatario: Usuario,

    @ManyToOne
    @JoinColumn(name = "lixeira_id")
    val lixeira: Lixeira? = null,

    @ManyToOne
    @JoinColumn(name = "coleta_id")
    val coleta: Coleta? = null,

    @Column(nullable = false)
    var lida: Boolean = false,

    @Column(nullable = false)
    val dataCriacao: LocalDateTime = LocalDateTime.now()
)
