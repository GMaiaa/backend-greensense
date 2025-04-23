package com.greensense.repository

import com.greensense.model.Coleta
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ColetaRepository : JpaRepository<Coleta, UUID> {
    fun findByLixeiraId(lixeiraId: UUID): List<Coleta>
}
