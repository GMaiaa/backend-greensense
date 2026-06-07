package com.greensense.service

import com.greensense.model.Coleta
import com.greensense.model.Lixeira
import com.greensense.repository.ColetaRepository
import com.greensense.repository.LixeiraRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class ColetaServiceTest {

    @Mock
    lateinit var coletaRepository: ColetaRepository

    @Mock
    lateinit var lixeiraRepository: LixeiraRepository

    @InjectMocks
    lateinit var service: ColetaService

    // CT01 - Fluxo Principal
    // RN01: Lixeira cadastrada e ativa
    // RN02: Quantidade coletada menor ou igual à capacidade máxima
    // RN03: Coleta associada a uma lixeira cadastrada
    @Test
    fun `deve registrar coleta com sucesso`() {
        val idLixeira = UUID.randomUUID()

        val lixeira = Lixeira(
            id = idLixeira,
            capacidadeMaxima = 100,
            tipo = "Orgânico",
            endereco = "Rua A",
            statusSensor = true
        )

        val coleta = Coleta(
            lixeiraId = idLixeira,
            quantidadeColetada = 50,
            responsavel = "João",
            metodo = "Manual",
            dataHora = LocalDateTime.now()
        )

        `when`(lixeiraRepository.findById(idLixeira)).thenReturn(Optional.of(lixeira))
        `when`(coletaRepository.save(any(Coleta::class.java))).thenReturn(coleta)

        val resultado = service.registrar(coleta)

        assertNotNull(resultado)
        assertEquals(idLixeira, resultado.lixeiraId)
        assertEquals(50, resultado.quantidadeColetada)
        verify(lixeiraRepository, times(1)).findById(idLixeira)
        verify(coletaRepository, times(1)).save(any(Coleta::class.java))
    }

    // CT02 - Fluxo Alternativo
    // Registro válido com observações complementares
    @Test
    fun `deve registrar coleta com observacoes complementares`() {
        val idLixeira = UUID.randomUUID()

        val lixeira = Lixeira(
            id = idLixeira,
            capacidadeMaxima = 100,
            tipo = "Reciclável",
            endereco = "Rua B",
            statusSensor = true
        )

        val coleta = Coleta(
            lixeiraId = idLixeira,
            quantidadeColetada = 70,
            responsavel = "Maria",
            metodo = "Manual",
            dataHora = LocalDateTime.now()
            // Se sua entidade Coleta tiver campo observacao, adicione:
            // observacao = "Coleta realizada sem intercorrências"
        )

        `when`(lixeiraRepository.findById(idLixeira)).thenReturn(Optional.of(lixeira))
        `when`(coletaRepository.save(any(Coleta::class.java))).thenReturn(coleta)

        val resultado = service.registrar(coleta)

        assertNotNull(resultado)
        assertEquals(70, resultado.quantidadeColetada)
        verify(coletaRepository, times(1)).save(any(Coleta::class.java))
    }

    // CT03 - Fluxo de Exceção
    // RN01/RN03: Não deve registrar coleta para lixeira inexistente
    @Test
    fun `nao deve registrar coleta se lixeira nao existir`() {
        val idLixeira = UUID.randomUUID()

        val coleta = Coleta(
            lixeiraId = idLixeira,
            quantidadeColetada = 50,
            responsavel = "Carlos",
            metodo = "Manual",
            dataHora = LocalDateTime.now()
        )

        `when`(lixeiraRepository.findById(idLixeira)).thenReturn(Optional.empty())

        val exception = assertThrows(RuntimeException::class.java) {
            service.registrar(coleta)
        }

        assertEquals("Lixeira não encontrada", exception.message)
        verify(lixeiraRepository, times(1)).findById(idLixeira)
        verify(coletaRepository, never()).save(any(Coleta::class.java))
    }

    // CT04 - Fluxo de Exceção
    // RN02: Quantidade coletada não pode exceder a capacidade máxima da lixeira
    @Test
    fun `nao deve registrar coleta se quantidade exceder capacidade maxima`() {
        val idLixeira = UUID.randomUUID()

        val lixeira = Lixeira(
            id = idLixeira,
            capacidadeMaxima = 100,
            tipo = "Orgânico",
            endereco = "Rua C",
            statusSensor = true
        )

        val coleta = Coleta(
            lixeiraId = idLixeira,
            quantidadeColetada = 150,
            responsavel = "Ana",
            metodo = "Manual",
            dataHora = LocalDateTime.now()
        )

        `when`(lixeiraRepository.findById(idLixeira)).thenReturn(Optional.of(lixeira))

        val exception = assertThrows(IllegalArgumentException::class.java) {
            service.registrar(coleta)
        }

        assertEquals("Quantidade coletada excede a capacidade da lixeira", exception.message)
        verify(lixeiraRepository, times(1)).findById(idLixeira)
        verify(coletaRepository, never()).save(any(Coleta::class.java))
    }

    // CT05 - Análise de Valor Limite
    // RN02: Quantidade igual à capacidade máxima deve ser aceita
    @Test
    fun `deve registrar coleta quando quantidade for igual a capacidade maxima`() {
        val idLixeira = UUID.randomUUID()

        val lixeira = Lixeira(
            id = idLixeira,
            capacidadeMaxima = 100,
            tipo = "Orgânico",
            endereco = "Rua D",
            statusSensor = true
        )

        val coleta = Coleta(
            lixeiraId = idLixeira,
            quantidadeColetada = 100,
            responsavel = "Lucas",
            metodo = "Manual",
            dataHora = LocalDateTime.now()
        )

        `when`(lixeiraRepository.findById(idLixeira)).thenReturn(Optional.of(lixeira))
        `when`(coletaRepository.save(any(Coleta::class.java))).thenReturn(coleta)

        val resultado = service.registrar(coleta)

        assertNotNull(resultado)
        assertEquals(100, resultado.quantidadeColetada)
        verify(coletaRepository, times(1)).save(any(Coleta::class.java))
    }

    // CT06 - Análise de Valor Limite
    // RN02: Quantidade uma unidade acima da capacidade máxima deve ser rejeitada
    @Test
    fun `nao deve registrar coleta quando quantidade for uma unidade acima da capacidade maxima`() {
        val idLixeira = UUID.randomUUID()

        val lixeira = Lixeira(
            id = idLixeira,
            capacidadeMaxima = 100,
            tipo = "Orgânico",
            endereco = "Rua E",
            statusSensor = true
        )

        val coleta = Coleta(
            lixeiraId = idLixeira,
            quantidadeColetada = 101,
            responsavel = "Pedro",
            metodo = "Manual",
            dataHora = LocalDateTime.now()
        )

        `when`(lixeiraRepository.findById(idLixeira)).thenReturn(Optional.of(lixeira))

        val exception = assertThrows(IllegalArgumentException::class.java) {
            service.registrar(coleta)
        }

        assertEquals("Quantidade coletada excede a capacidade da lixeira", exception.message)
        verify(coletaRepository, never()).save(any(Coleta::class.java))
    }
}