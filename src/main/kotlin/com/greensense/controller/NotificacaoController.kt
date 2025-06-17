package com.greensense.controller

import com.greensense.model.Notificacao
import com.greensense.repository.NotificacaoRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/notificacoes")
class NotificacaoController(
    private val repository: NotificacaoRepository
) {

    @GetMapping
    fun listar(): List<Notificacao> = repository.findAll()

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun criar(@RequestBody notificacao: Notificacao): Notificacao =
        repository.save(notificacao)

    @PatchMapping("/{id}/ler")
    fun marcarComoLida(@PathVariable id: String): ResponseEntity<Void> {
        val notificacao = repository.findById(id)
        return if (notificacao.isPresent) {
            val n = notificacao.get()
            n.lida = true
            repository.save(n)
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun deletar(@PathVariable id: String): ResponseEntity<Void> {
        return if (repository.existsById(id)) {
            repository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
