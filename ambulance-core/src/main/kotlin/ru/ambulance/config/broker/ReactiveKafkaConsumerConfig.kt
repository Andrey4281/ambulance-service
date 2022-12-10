package ru.ambulance.config.broker

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.kafka.support.DefaultKafkaHeaderMapper
import org.springframework.messaging.MessageHeaders
import reactor.core.publisher.Mono
import reactor.kafka.receiver.ReceiverOptions
import reactor.kafka.sender.SenderResult
import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.function.logger


/**
 * Общий конфигурационный класс для Kafka Consumer
 */
@Configuration
abstract class ReactiveKafkaConsumer<T : BaseEvent, D : Any> {

    @Autowired
    private lateinit var reactiveKafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, String>

    @Autowired
    private lateinit var defaultKafkaHeaderMapper: DefaultKafkaHeaderMapper

    private val log = logger()

    @Value("\${kafka.retry.count}")
    private val retryCount: Int = 3

    @Value("\${spring.application.name}")
    private val applicationName: String = ""

    abstract fun consumer(kafkaProperties: KafkaProperties,
                          objectMapper: ObjectMapper) : ApplicationRunner

    open fun abstractConsumer(kafkaProperties: KafkaProperties,
                              objectMapper: ObjectMapper):
            ApplicationRunner {
        return ApplicationRunner {
            run {

                val receiverOptions: ReceiverOptions<String, String> = ReceiverOptions.create<String, String>(kafkaProperties.buildConsumerProperties())
                        .subscription(listOf(getTopic()))
                val reactiveKafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<String, String> =
                        ReactiveKafkaConsumerTemplate(receiverOptions)

                reactiveKafkaConsumerTemplate
                        .receiveAutoAck()
                        .doOnNext { log.info("topic=${it.topic()} key=${it.key()} value=${it.value()} offset=${it.offset()}") }
                        .flatMap {
                            val consumerRecord = it
                            getSuccessHandler(objectMapper.readValue(it.value(), getEventClass()))
                                    .onErrorResume { getErrorHandler(consumerRecord, it).then(Mono.just(getErrorObject()))}
                        }
                        .subscribe()
            }
        }
    }

    abstract fun getErrorObject(): D

    abstract fun getTopic(): String

    abstract fun getSuccessHandler(value: T): Mono<D>

    //TODO asemenov сделать слушатель на dead letter топик который будет откатывать саги
    private fun getErrorHandler(consumerRecord: ConsumerRecord<String, String>, e: Throwable): Mono<SenderResult<Void>> {
        val consumerRecordHeaders: Map<String, Any> = HashMap()
        defaultKafkaHeaderMapper.toHeaders(consumerRecord.headers(), consumerRecordHeaders)
        val currentRetryCount: Int = (consumerRecordHeaders["currentRetryCount"] ?: retryCount) as Int

        val errorProducerRecord: ProducerRecord<String, String> = if (currentRetryCount > 0) {
            ProducerRecord(getRetryTopicName(), consumerRecord.key(), consumerRecord.value())
        } else {
            ProducerRecord(getDeadLetterTopicName(), consumerRecord.key(), consumerRecord.value())
        }

        val producerHeaders: MutableMap<String, Any> = HashMap()
        producerHeaders.putAll(consumerRecordHeaders)
        producerHeaders["currentRetryCount"] = currentRetryCount
        producerHeaders["targetTopic"] = consumerRecord.topic()
        producerHeaders["applicationName"] = applicationName
        val messageHeaders = MessageHeaders(producerHeaders)
        defaultKafkaHeaderMapper.fromHeaders(messageHeaders, errorProducerRecord.headers())

        return reactiveKafkaProducerTemplate.send(errorProducerRecord)
                .doOnSuccess { log.error("Error message was sent to topic ${it.recordMetadata().topic()}") }
    }

    abstract fun getEventClass(): Class<T>

    abstract fun getRetryTopicName(): String

    abstract fun getDeadLetterTopicName(): String
}