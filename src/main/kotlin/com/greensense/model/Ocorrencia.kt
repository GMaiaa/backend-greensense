
package com.greensense.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Ocorrencia(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val tipo: String,

    val descricao: String,

    val nomeUsuario: String,

    @ManyToOne
    val lixeira: Lixeira,

    val data: LocalDateTime = LocalDateTime.now()
)
