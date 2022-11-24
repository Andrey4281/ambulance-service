package ru.ambulance.config.broker

import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.sender.SenderOptions

@Configuration
open class ReactiveKafkaProducerConfig {

    @Bean
    open fun reactiveKafkaProducerTemplate(properties: KafkaProperties) : ReactiveKafkaProducerTemplate<String, String> {
        val propertiesMap = properties.buildProducerProperties()
        return ReactiveKafkaProducerTemplate(SenderOptions.create(propertiesMap))
    }
}