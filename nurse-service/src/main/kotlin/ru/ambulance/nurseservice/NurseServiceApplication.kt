package ru.ambulance.nurseservice

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication(scanBasePackages = arrayOf(
        "ru.ambulance.config.broker",
        "ru.ambulance.config.dao",
        "ru.ambulance.config.scheduler",
        "ru.ambulance.broker.retry",
        "ru.ambulance.broker.service",
        "ru.ambulance.nurseservice"))
@EnableWebFlux
@OpenAPIDefinition(info = Info(title = "Swagger Doctor Service", version = "1.0", description = "Documentation APIs v1.0"))
class NurseServiceApplication

fun main(args: Array<String>) {
    runApplication<NurseServiceApplication>(*args)
}
