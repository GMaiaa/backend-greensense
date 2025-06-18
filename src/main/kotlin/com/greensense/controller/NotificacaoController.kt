package com.greensense.controller

import com.greensense.dto.notificacao.NotificacaoRequest
import com.greensense.model.Notificacao
import com.greensense.service.NotificacaoService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notificacoes")
class NotificacaoController(
    private val service: NotificacaoService
) {

    @GetMapping
    fun listar(): List<Notificacao> = service.listar()

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun criar(@RequestBody request: NotificacaoRequest): Notificacao =
        service.salvar(request)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun atualizar(@PathVariable id: String, @RequestBody request: NotificacaoRequest): ResponseEntity<Notificacao> {
        val notificacaoAtualizada = service.salvar(request)
        return ResponseEntity.ok(notificacaoAtualizada)
    }

    @GetMapping("/{id}")
fun buscarPorId(@PathVariable id: String): ResponseEntity<Notificacao> {
    val notificacao = service.buscarPorId(id)
    return if (notificacao != null) {
        ResponseEntity.ok(notificacao)
    } else {
        ResponseEntity.notFound().build()
    }
}

    @PatchMapping("/{id}/ler")
    fun marcarComoLida(@PathVariable id: String): ResponseEntity<Void> {
        return if (service.marcarComoLida(id)) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun deletar(@PathVariable id: String): ResponseEntity<Void> {
        return try {
            service.deletar(id)
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
}
