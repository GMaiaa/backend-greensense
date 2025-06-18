package com.greensense.service

import com.greensense.model.Lixeira
import com.greensense.repository.LixeiraRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class LixeiraService(private val repository: LixeiraRepository) {

    fun cadastrar(lixeira: Lixeira): Lixeira = repository.save(lixeira)

    fun listarTodas(): List<Lixeira> = repository.findAll()

    fun atualizar(id: UUID, lixeiraAtualizada: Lixeira): Lixeira {
        val lixeiraExistente = repository.findById(id)
            .orElseThrow { RuntimeException("Lixeira não encontrada") }

        val lixeira = lixeiraExistente.copy(
            tipo = lixeiraAtualizada.tipo,
            endereco = lixeiraAtualizada.endereco,
            capacidadeMaxima = lixeiraAtualizada.capacidadeMaxima,
            nivelAtual = lixeiraAtualizada.nivelAtual,
            statusSensor = lixeiraAtualizada.statusSensor,
            sensorId = lixeiraAtualizada.sensorId
        )

        return repository.save(lixeira)
    }

    fun buscarPorId(id: UUID): Lixeira? = repository.findById(id).orElse(null)

    fun deletar(id: UUID) = repository.deleteById(id)
}
