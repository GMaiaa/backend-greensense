package com.greensense.controller

import com.greensense.dto.revisao.RevisaoRequest
import com.greensense.model.Revisao
import com.greensense.service.RevisaoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/revisao")
class RevisaoController(
    private val service: RevisaoService
) {
    @GetMapping
    fun listar(): List<Revisao> = service.listar()

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long) = service.buscarPorId(id)

    @PostMapping
    fun salvar(@RequestBody request: RevisaoRequest) = service.salvar(request)

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody request: RevisaoRequest) =
        service.atualizar(id, request)

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long) = service.deletar(id)
}
