package com.greensense.repository

import com.greensense.model.Notificacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificacaoRepository : JpaRepository<Notificacao, String>
