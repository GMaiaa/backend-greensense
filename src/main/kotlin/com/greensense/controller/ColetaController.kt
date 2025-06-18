package com.greensense.controller

import com.greensense.model.Coleta
import com.greensense.service.ColetaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/coletas")
class ColetaController(private val service: ColetaService) {

    @PostMapping
    fun registrar(@RequestBody coleta: Coleta): Coleta = service.registrar(coleta)

    @GetMapping
    fun listarTodas(): List<Coleta> = service.listarTodas()

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: UUID): ResponseEntity<Coleta> {
        val coleta = service.buscarPorId(id)
        return if (coleta != null) ResponseEntity.ok(coleta)
        else ResponseEntity.notFound().build()
    }

@PutMapping("/{id}")
fun atualizar(@PathVariable id: UUID, @RequestBody coleta: Coleta): ResponseEntity<Coleta> {
    val coletaAtualizada = service.atualizar(id, coleta)
    return ResponseEntity.ok(coletaAtualizada)
}

@DeleteMapping("/{id}")
fun deletar(@PathVariable id: UUID): ResponseEntity<Void> {
    return if (service.buscarPorId(id) != null) {
        service.deletar(id)
        ResponseEntity.noContent().build()
    } else {
        ResponseEntity.notFound().build()
    }
}


    @GetMapping("/lixeira/{lixeiraId}")
    fun listarPorLixeira(@PathVariable lixeiraId: UUID): List<Coleta> =
        service.listarPorLixeira(lixeiraId)
}
