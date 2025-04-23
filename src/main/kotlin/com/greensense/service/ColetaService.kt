package com.greensense.service

import com.greensense.model.Coleta
import com.greensense.repository.ColetaRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ColetaService(private val repository: ColetaRepository) {

    fun registrar(coleta: Coleta): Coleta = repository.save(coleta)

    fun listarTodas(): List<Coleta> = repository.findAll()

    fun buscarPorId(id: UUID): Coleta? = repository.findById(id).orElse(null)

    fun listarPorLixeira(lixeiraId: UUID): List<Coleta> = repository.findByLixeiraId(lixeiraId)
}
