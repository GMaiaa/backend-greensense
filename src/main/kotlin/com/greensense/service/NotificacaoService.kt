package com.greensense.service

import com.greensense.dto.notificacao.NotificacaoRequest
import com.greensense.model.Notificacao
import com.greensense.repository.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificacaoService(
    private val repository: NotificacaoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val lixeiraRepository: LixeiraRepository,
    private val coletaRepository: ColetaRepository
) {
    fun listar(): List<Notificacao> = repository.findAll()

    fun buscarPorId(id: String): Notificacao? = repository.findById(id).orElse(null)

    fun salvar(request: NotificacaoRequest): Notificacao {
        val destinatario = usuarioRepository.findById(request.destinatarioId)
            .orElseThrow { RuntimeException("Destinatário não encontrado") }

        val lixeira = request.lixeiraId?.let {
            lixeiraRepository.findById(it).orElse(null)
        }

        val coleta = request.coletaId?.let {
            coletaRepository.findById(it).orElse(null)
        }

        val notificacao = Notificacao(
            titulo = request.titulo,
            mensagem = request.mensagem,
            tipo = request.tipo,
            lida = request.lida,
            destinatario = destinatario,
            lixeira = lixeira,
            coleta = coleta
        )

        return repository.save(notificacao)
    }

    fun deletar(id: String) = repository.deleteById(id)

    fun marcarComoLida(id: String): Boolean {
        val notificacao = repository.findById(id)
            .orElseThrow { RuntimeException("Notificação não encontrada") }

        notificacao.lida = true
        repository.save(notificacao)
        return true
    }
}
