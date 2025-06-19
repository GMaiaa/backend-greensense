package com.greensense.repository

import com.greensense.model.MaterialColetado
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MaterialColetadoRepository : JpaRepository<MaterialColetado, Long> {
    fun findByLixeiraId(lixeiraId: UUID): List<MaterialColetado>
}