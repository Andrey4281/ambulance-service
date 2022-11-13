package ru.ambulance.appeal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux


@EnableWebFlux
@SpringBootApplication
class AppealServiceStarter

fun main(args: Array<String>) {
    runApplication<AppealServiceStarter>(*args)
}