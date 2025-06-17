package com.greensense.service

import com.greensense.dto.material.MaterialColetadoRequest
import com.greensense.model.MaterialColetado
import com.greensense.repository.LixeiraRepository
import com.greensense.repository.MaterialColetadoRepository
import org.springframework.stereotype.Service

@Service
class MaterialColetadoService(
    private val repository: MaterialColetadoRepository,
    private val lixeiraRepository: LixeiraRepository
) {
    fun listar(): List<MaterialColetado> = repository.findAll()

    fun buscarPorId(id: Long): MaterialColetado? = repository.findById(id).orElse(null)

    fun salvar(request: MaterialColetadoRequest): MaterialColetado {
        val lixeira = lixeiraRepository.findById(request.lixeiraId)
            .orElseThrow { RuntimeException("Lixeira não encontrada") }

        val material = MaterialColetado(
            tipo = request.tipo,
            quantidade = request.quantidade,
            unidade = request.unidade,
            nomeUsuario = request.nomeUsuario,
            lixeira = lixeira
        )

        return repository.save(material)
    }

    fun atualizar(id: Long, request: MaterialColetadoRequest): MaterialColetado {
        val material = repository.findById(id)
            .orElseThrow { RuntimeException("Material coletado não encontrado") }

        val lixeira = lixeiraRepository.findById(request.lixeiraId)
            .orElseThrow { RuntimeException("Lixeira não encontrada") }

        val materialAtualizado = material.copy(
            tipo = request.tipo,
            quantidade = request.quantidade,
            unidade = request.unidade,
            nomeUsuario = request.nomeUsuario,
            lixeira = lixeira
        )

        return repository.save(materialAtualizado)
    }

    fun deletar(id: Long) = repository.deleteById(id)
}
