package com.greensense.controller

import com.greensense.dto.material.MaterialColetadoRequest
import com.greensense.model.MaterialColetado
import com.greensense.service.MaterialColetadoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/material")
class MaterialColetadoController(
    private val service: MaterialColetadoService
) {
    @GetMapping
    fun listar(): List<MaterialColetado> = service.listar()

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long) = service.buscarPorId(id)

    @PostMapping
    fun salvar(@RequestBody request: MaterialColetadoRequest) = service.salvar(request)

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody request: MaterialColetadoRequest) =
        service.atualizar(id, request)

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long) = service.deletar(id)
}
