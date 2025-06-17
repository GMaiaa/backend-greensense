package com.greensense.service

import com.greensense.dto.ocorrencia.OcorrenciaRequest
import com.greensense.model.Ocorrencia
import com.greensense.repository.LixeiraRepository
import com.greensense.repository.OcorrenciaRepository
import org.springframework.stereotype.Service

@Service
class OcorrenciaService(
    private val repository: OcorrenciaRepository,
    private val lixeiraRepository: LixeiraRepository
) {
    fun listar(): List<Ocorrencia> = repository.findAll()

    fun buscarPorId(id: Long): Ocorrencia? = repository.findById(id).orElse(null)

    fun salvar(request: OcorrenciaRequest): Ocorrencia {
       val lixeira = lixeiraRepository.findById(request.lixeiraId)
    .orElseThrow { RuntimeException("Lixeira não encontrada") }


        val ocorrencia = Ocorrencia(
            tipo = request.tipo,
            descricao = request.descricao,
            nomeUsuario = request.nomeUsuario,
            lixeira = lixeira
        )

        return repository.save(ocorrencia)
    }

    fun atualizar(id: Long, request: OcorrenciaRequest): Ocorrencia {
    val ocorrencia = repository.findById(id)
        .orElseThrow { RuntimeException("Ocorrência não encontrada") }

    val lixeira = lixeiraRepository.findById(request.lixeiraId)
        .orElseThrow { RuntimeException("Lixeira não encontrada") }

    val ocorrenciaAtualizada = ocorrencia.copy(
        tipo = request.tipo,
        descricao = request.descricao,
        nomeUsuario = request.nomeUsuario,
        lixeira = lixeira
    )

    return repository.save(ocorrenciaAtualizada)
}


    fun deletar(id: Long) = repository.deleteById(id)
}
