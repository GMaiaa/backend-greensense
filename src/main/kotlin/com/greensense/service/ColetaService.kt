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

   fun atualizar(id: UUID, coletaAtualizada: Coleta): Coleta {
    val coletaExistente = repository.findById(id)
        .orElseThrow { RuntimeException("Coleta não encontrada") }

    val coleta = coletaExistente.copy(
        lixeiraId = coletaAtualizada.lixeiraId,
        dataHora = coletaAtualizada.dataHora,
        quantidadeColetada = coletaAtualizada.quantidadeColetada,
        responsavel = coletaAtualizada.responsavel,
        metodo = coletaAtualizada.metodo
    )

    return repository.save(coleta)
}

fun deletar(id: UUID) = repository.deleteById(id)

    fun listarPorLixeira(lixeiraId: UUID): List<Coleta> = repository.findByLixeiraId(lixeiraId)
}
