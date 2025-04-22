package com.greensense

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GreensenseBackendApplication

fun main(args: Array<String>) {
	runApplication<GreensenseBackendApplication>(*args)
}
