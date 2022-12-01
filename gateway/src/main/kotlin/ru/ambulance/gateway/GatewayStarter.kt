package ru.ambulance.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@SpringBootApplication
class GatewayStarter

fun main(args: Array<String>) {
    runApplication<GatewayStarter>(*args)
}
