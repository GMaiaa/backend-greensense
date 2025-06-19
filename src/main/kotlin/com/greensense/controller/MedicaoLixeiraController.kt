package com.greensense.controller

import com.greensense.model.MedicaoLixeira
import com.greensense.repository.MedicaoLixeiraRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/medicoes")
class MedicaoLixeiraController(
    private val repository: MedicaoLixeiraRepository
) {

    @GetMapping
    fun listarTodas(): List<MedicaoLixeira> {
        return repository.findAll()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): MedicaoLixeira {
        return repository.findById(id).orElseThrow {
            RuntimeException("Medição não encontrada para id $id")
        }
    }

    @GetMapping("/lixeira/{idLixeira}")
    fun buscarPorLixeira(@PathVariable idLixeira: UUID): List<MedicaoLixeira> {
        return repository.findAll().filter { it.idLixeira == idLixeira }
    }

    @DeleteMapping("/{id}")
    fun deletarPorId(@PathVariable id: Long) {
        repository.deleteById(id)
    }
}
