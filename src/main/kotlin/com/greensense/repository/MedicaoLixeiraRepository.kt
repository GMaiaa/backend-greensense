package com.greensense.repository

import com.greensense.model.MedicaoLixeira
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MedicaoLixeiraRepository : JpaRepository<MedicaoLixeira, Long>
