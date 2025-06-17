package com.greensense.dto.material

import java.util.UUID

data class MaterialColetadoRequest(
    val tipo: String,
    val quantidade: Double,
    val unidade: String,
    val nomeUsuario: String,
    val lixeiraId: UUID
)
