package ru.ambulance.appeal

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
        "ru.ambulance.appeal"))
@EnableWebFlux
@OpenAPIDefinition(info = Info(title = "Swagger Appeal Service", version = "1.0", description = "Documentation APIs v1.0"))
class AppealServiceStarter

fun main(args: Array<String>) {
    runApplication<AppealServiceStarter>(*args)
}