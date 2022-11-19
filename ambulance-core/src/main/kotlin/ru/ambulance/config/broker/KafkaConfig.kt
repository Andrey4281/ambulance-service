package ru.ambulance.config.broker

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.DefaultKafkaHeaderMapper

@Configuration
open class KafkaConfig {

    @Bean
    open fun defaultKafkaHeaderMapper(): DefaultKafkaHeaderMapper = DefaultKafkaHeaderMapper()
}