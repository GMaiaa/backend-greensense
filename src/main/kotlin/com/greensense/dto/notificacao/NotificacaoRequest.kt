package com.greensense.dto.notificacao

import java.util.UUID

data class NotificacaoRequest(
    val titulo: String,
    val mensagem: String,
    val tipo: String,
    val lida: Boolean = false,
    val destinatarioId: UUID,
    val lixeiraId: UUID?,
    val coletaId: UUID?
)