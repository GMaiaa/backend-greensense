
package com.greensense.repository

import com.greensense.model.Ocorrencia
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OcorrenciaRepository : JpaRepository<Ocorrencia, Long>
