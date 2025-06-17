
package com.greensense.repository

import com.greensense.model.Revisao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RevisaoRepository : JpaRepository<Revisao, Long>
