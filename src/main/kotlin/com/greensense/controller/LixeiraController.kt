package com.greensense.controller

import com.greensense.model.Lixeira
import com.greensense.service.LixeiraService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/lixeiras")
class LixeiraController(private val service: LixeiraService) {

    @GetMapping
    fun listarTodas(): List<Lixeira> = service.listarTodas()

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: UUID): ResponseEntity<Lixeira> {
        val lixeira = service.buscarPorId(id)
        return if (lixeira != null) ResponseEntity.ok(lixeira)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun cadastrar(@RequestBody lixeira: Lixeira): Lixeira = service.cadastrar(lixeira)
}
