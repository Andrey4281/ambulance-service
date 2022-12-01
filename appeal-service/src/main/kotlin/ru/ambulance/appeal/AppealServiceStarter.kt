package ru.ambulance.appeal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.reactive.config.EnableWebFlux


@SpringBootApplication(scanBasePackages = arrayOf(
        "ru.ambulance.config.broker",
        "ru.ambulance.config.dao",
        "ru.ambulance.config.scheduler",
        "ru.ambulance.broker.retry",
        "ru.ambulance.broker.service",
        "ru.ambulance.appeal"))
@EnableWebFlux
@EnableTransactionManagement
@EnableScheduling
class AppealServiceStarter

fun main(args: Array<String>) {
    runApplication<AppealServiceStarter>(*args)
}