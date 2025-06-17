package com.greensense.dto.revisao

import java.util.UUID

data class RevisaoRequest(
    val descricao: String,
    val status: String,
    val nomeUsuario: String,
    val lixeiraId: UUID
)
