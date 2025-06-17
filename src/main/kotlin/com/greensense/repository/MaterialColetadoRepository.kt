package com.greensense.repository

import com.greensense.model.MaterialColetado
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MaterialColetadoRepository : JpaRepository<MaterialColetado, Long>
