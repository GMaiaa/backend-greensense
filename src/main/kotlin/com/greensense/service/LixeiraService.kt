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

    fun deletar(id: UUID): Boolean {
        val lixeira = repository.findById(id)
        return if (lixeira.isPresent) {
            repository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun atualizar(id: UUID, lixeira: Lixeira): Lixeira? {
        val existente = repository.findById(id)
        return if (existente.isPresent) {
            val lixeiraAtualizada = existente.get().copy(
                tipo = lixeira.tipo,
                endereco = lixeira.endereco,
                capacidadeMaxima = lixeira.capacidadeMaxima,
                nivelAtual = lixeira.nivelAtual,
                statusSensor = lixeira.statusSensor,
                sensorId = lixeira.sensorId
            )
            repository.save(lixeiraAtualizada)
        } else {
            null
        }
    }
}
