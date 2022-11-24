package ru.ambulance.broker.retry

import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.kafka.support.DefaultKafkaHeaderMapper
import org.springframework.messaging.MessageHeaders
import reactor.kafka.receiver.ReceiverOptions
import ru.ambulance.function.logger

@Configuration
open class RetryListener {

    private val log = logger()

    @Value("\${kafka.retry.retryTopic}")
    private val retryTopic: String = "retryTopic"

    @Bean
    open fun retry(kafkaProperties: KafkaProperties,
                   defaultKafkaHeaderMapper: DefaultKafkaHeaderMapper,
                   reactiveKafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, String>): ApplicationRunner {
        return ApplicationRunner {
            val receiverOptions: ReceiverOptions<String, String> = ReceiverOptions.create<String, String>(kafkaProperties.buildConsumerProperties())
                    .subscription(listOf(retryTopic))
            val reactiveKafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<String, String> =
                    ReactiveKafkaConsumerTemplate(receiverOptions)

            reactiveKafkaConsumerTemplate
                    .receiveAutoAck()
                    .doOnNext { log.info("topic=${it.topic()} key=${it.key()} value=${it.value()} offset=${it.offset()}") }
                    .flatMap {
                        val consumerRecordHeaders: Map<String, Any> = HashMap()
                        defaultKafkaHeaderMapper.toHeaders(it.headers(), consumerRecordHeaders)

                        val recoveredProducerRecord: ProducerRecord<String, String> =
                                ProducerRecord(consumerRecordHeaders["targetTopic"] as String?, it.key(), it.value())

                        val producerHeaders: MutableMap<String, Any> = HashMap()
                        producerHeaders.putAll(consumerRecordHeaders)
                        producerHeaders["currentRetryCount"] =  ((producerHeaders["currentRetryCount"] as Int?)?.minus(1)) as Any
                        val messageHeaders = MessageHeaders(producerHeaders)
                        defaultKafkaHeaderMapper.fromHeaders(messageHeaders, recoveredProducerRecord.headers())

                        reactiveKafkaProducerTemplate.send(recoveredProducerRecord)
                                .doOnSuccess { log.error("Recovered message was sent to topic from retry topic ${it.recordMetadata().topic()}") }
                    }
                    .subscribe()
        }
    }
}