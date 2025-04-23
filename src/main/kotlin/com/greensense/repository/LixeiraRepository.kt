package com.greensense.repository

import com.greensense.model.Lixeira
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LixeiraRepository : JpaRepository<Lixeira, UUID>
