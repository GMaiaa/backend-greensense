package com.greensense.dto.ocorrencia

data class OcorrenciaResponse(
    val id: Long,
    val descricao: String,
    val lixeiraId: Long,
    val usuarioId: Long
)
