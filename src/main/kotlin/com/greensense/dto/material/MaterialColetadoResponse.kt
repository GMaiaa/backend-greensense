
package com.greensense.dto.material

data class MaterialColetadoResponse(
    val id: Long,
    val descricao: String,
    val lixeiraId: Long,
    val usuarioId: Long
)
