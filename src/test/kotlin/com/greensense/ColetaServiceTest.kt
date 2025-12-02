package com.greensense.service

import com.greensense.model.Coleta
import com.greensense.model.Lixeira
import com.greensense.repository.ColetaRepository
import com.greensense.repository.LixeiraRepository
import com.greensense.service.ColetaService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class ColetaServiceTest {

    @Mock
    lateinit var repository: ColetaRepository

    @Mock
    lateinit var lixeiraRepository: LixeiraRepository

    @InjectMocks
    lateinit var service: ColetaService

    // --- TESTE 1: Caminho Feliz (Sucesso) ---
    @Test
    fun `deve registrar coleta com sucesso`() {
        val idLixeira = UUID.randomUUID()

        // Mock da Lixeira (Ativa e com capacidade)
        val lixeiraMock = Lixeira(
            id = idLixeira,
            capacidadeMaxima = 100,
            tipo = "Orgânico",
            endereco = "Rua A",
            statusSensor = true
        )

        // Mock da Coleta (Válida)
        val novaColeta = Coleta(
            lixeiraId = idLixeira,
            quantidadeColetada = 50,
            responsavel = "João",
            metodo = "Manual",
            dataHora = LocalDateTime.now()
        )

        `when`(lixeiraRepository.findById(idLixeira)).thenReturn(Optional.of(lixeiraMock))
        `when`(repository.save(any(Coleta::class.java))).thenReturn(novaColeta)

        val resultado = service.registrar(novaColeta)

        assertNotNull(resultado)
        verify(repository, times(1)).save(any(Coleta::class.java))
    }

    // --- TESTE 2: Regra de Negócio RN04 (Capacidade) ---
    @Test
    fun `nao deve registrar se exceder capacidade (RN04)`() {
        val idLixeira = UUID.randomUUID()

        val lixeiraMock = Lixeira(
            id = idLixeira,
            capacidadeMaxima = 100, // Cabe 100
            tipo = "Orgânico",
            endereco = "Rua A",
            statusSensor = true
        )

        val coletaInvalida = Coleta(
            lixeiraId = idLixeira,
            quantidadeColetada = 150, // Tenta pôr 150
            responsavel = "Maria",
            metodo = "Auto",
            dataHora = LocalDateTime.now()
        )

        `when`(lixeiraRepository.findById(idLixeira)).thenReturn(Optional.of(lixeiraMock))

        val exception = assertThrows(IllegalArgumentException::class.java) {
            service.registrar(coletaInvalida)
        }

        assertEquals("Quantidade coletada excede a capacidade da lixeira", exception.message)
        verify(repository, never()).save(any(Coleta::class.java))
    }

    // --- TESTE 3: Regra de Negócio RN01 (Lixeira Inexistente) ---
    // Esse era o único que faltava para cobrir a linha do "orElseThrow"
    @Test
    fun `nao deve registrar se lixeira nao existir (RN01)`() {
        val idLixeira = UUID.randomUUID()

        val novaColeta = Coleta(
            lixeiraId = idLixeira,
            quantidadeColetada = 50,
            responsavel = "Teste",
            metodo = "Manual",
            dataHora = LocalDateTime.now()
        )

        // Simula que o banco NÃO achou nada (Optional Vazio)
        `when`(lixeiraRepository.findById(idLixeira)).thenReturn(Optional.empty())

        val exception = assertThrows(RuntimeException::class.java) {
            service.registrar(novaColeta)
        }

        assertEquals("Lixeira não encontrada", exception.message)
        verify(repository, never()).save(any(Coleta::class.java))
    }
}