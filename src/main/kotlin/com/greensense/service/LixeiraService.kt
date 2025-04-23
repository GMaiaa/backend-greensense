package com.greensense.service

import com.greensense.model.Lixeira
import com.greensense.repository.LixeiraRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class LixeiraService(private val repository: LixeiraRepository) {

    fun cadastrar(lixeira: Lixeira): Lixeira = repository.save(lixeira)

    fun listarTodas(): List<Lixeira> = repository.findAll()

    fun buscarPorId(id: UUID): Lixeira? = repository.findById(id).orElse(null)
}
