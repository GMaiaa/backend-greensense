
package com.greensense.dto.ocorrencia

import java.util.UUID

data class OcorrenciaRequest(
    val tipo: String,
    val descricao: String,
    val nomeUsuario: String,
    val lixeiraId: UUID
)


