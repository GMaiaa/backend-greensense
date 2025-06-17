package com.greensense.controller

import com.greensense.dto.ocorrencia.OcorrenciaRequest
import com.greensense.model.Ocorrencia
import com.greensense.service.OcorrenciaService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ocorrencia")
class OcorrenciaController(
    private val service: OcorrenciaService
) {
    @GetMapping
    fun listar(): List<Ocorrencia> = service.listar()

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long) = service.buscarPorId(id)

    @PostMapping
    fun salvar(@RequestBody request: OcorrenciaRequest) = service.salvar(request)

    @PutMapping("/{id}")
fun atualizar(@PathVariable id: Long, @RequestBody request: OcorrenciaRequest): Ocorrencia {
    return service.atualizar(id, request)
}

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long) = service.deletar(id)
}
