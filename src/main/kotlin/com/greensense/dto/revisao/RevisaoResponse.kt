
package com.greensense.dto.revisao

import java.util.UUID

data class RevisaoResponse(
    val id: Long,
    val descricao: String,
    val nomeUsuario: String,
    val lixeiraId: UUID

)
