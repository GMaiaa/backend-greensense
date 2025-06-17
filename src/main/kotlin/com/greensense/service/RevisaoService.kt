package com.greensense.service

import com.greensense.dto.revisao.RevisaoRequest
import com.greensense.model.Revisao
import com.greensense.repository.LixeiraRepository
import com.greensense.repository.RevisaoRepository
import org.springframework.stereotype.Service

@Service
class RevisaoService(
    private val repository: RevisaoRepository,
    private val lixeiraRepository: LixeiraRepository
) {
    fun listar(): List<Revisao> = repository.findAll()

    fun buscarPorId(id: Long): Revisao? = repository.findById(id).orElse(null)

    fun salvar(request: RevisaoRequest): Revisao {
        val lixeira = lixeiraRepository.findById(request.lixeiraId)
            .orElseThrow { RuntimeException("Lixeira não encontrada") }

        val revisao = Revisao(
            descricao = request.descricao,
            status = request.status,
            nomeUsuario = request.nomeUsuario,
            lixeira = lixeira
        )

        return repository.save(revisao)
    }

    fun atualizar(id: Long, request: RevisaoRequest): Revisao {
        val revisao = repository.findById(id)
            .orElseThrow { RuntimeException("Revisão não encontrada") }

        val lixeira = lixeiraRepository.findById(request.lixeiraId)
            .orElseThrow { RuntimeException("Lixeira não encontrada") }

        val revisaoAtualizada = revisao.copy(
            descricao = request.descricao,
            status = request.status,
            nomeUsuario = request.nomeUsuario,
            lixeira = lixeira
        )

        return repository.save(revisaoAtualizada)
    }

    fun deletar(id: Long) = repository.deleteById(id)
}
