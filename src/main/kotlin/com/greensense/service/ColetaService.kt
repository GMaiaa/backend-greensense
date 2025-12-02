package com.greensense.service

import com.greensense.model.Coleta
import com.greensense.repository.ColetaRepository
import com.greensense.repository.LixeiraRepository // <--- NOVO IMPORT
import org.springframework.stereotype.Service
import java.util.*

@Service
class ColetaService(
    private val repository: ColetaRepository,
    private val lixeiraRepository: LixeiraRepository // <--- INJEÇÃO DA DEPENDENCIA NOVA
) {

    // ALTERADO: Agora valida as regras antes de salvar
    fun registrar(coleta: Coleta): Coleta {
        // 1. Validação RN01: A lixeira existe?
        // Precisamos buscar a lixeira para saber a capacidade dela
        val lixeira = lixeiraRepository.findById(coleta.lixeiraId)
            .orElseThrow { RuntimeException("Lixeira não encontrada") }

        // 2. Validação RN04: A quantidade excede a capacidade?
        if (coleta.quantidadeColetada > lixeira.capacidadeMaxima) {
            throw IllegalArgumentException("Quantidade coletada excede a capacidade da lixeira")
        }

        // 3. Se passou pelas regras, salva no banco
        return repository.save(coleta)
    }

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