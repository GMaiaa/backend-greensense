package com.greensense.mqtt

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.greensense.model.MedicaoLixeira
import com.greensense.repository.MedicaoLixeiraRepository
import jakarta.annotation.PostConstruct
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class MqttListener(
    private val repository: MedicaoLixeiraRepository
) {

    private val broker = "tcp://broker.hivemq.com:1883"
    private val clientId = MqttClient.generateClientId()
    private val topic = "/greensense/lixeira/01"

    @PostConstruct
    fun start() {
        try {
            val client = MqttClient(broker, clientId)
            val options = MqttConnectOptions()
            options.isCleanSession = true

            client.connect(options)
            client.subscribe(topic) { _, message ->
                val payload = String(message.payload)
                println("Mensagem recebida: $payload")

                val mapper = jacksonObjectMapper()
                val json = mapper.readTree(payload)

                val medicao = MedicaoLixeira(
                    idLixeira = UUID.fromString(json["idLixeira"].asText()),
                    distancia = json["distancia"].asDouble(),
                    dataHora = LocalDateTime.now()
                )

                repository.save(medicao)
                println("Medição salva no banco: $medicao")
            }

        } catch (e: MqttException) {
            println("Erro na conexão MQTT: ${e.message}")
            e.printStackTrace()
        } catch (e: Exception) {
            println("Erro geral: ${e.message}")
            e.printStackTrace()
        }
    }
}
