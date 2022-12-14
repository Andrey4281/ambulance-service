package ru.ambulance.broker.service

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderRecord
import ru.ambulance.broker.events.outbox.OutboxEvent
import ru.ambulance.function.logger

abstract class OutboxProcessor {

    private val log = logger()

    @Autowired
    private lateinit var reactiveKafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, String>

    @Autowired
    private lateinit var  transactionalOperator: TransactionalOperator

    @Scheduled(fixedDelayString = "\${outbox.fixed-delay-sending-in-milliseconds:5000}")
    fun sendMessages() {
        getMessage().log().flatMap {
            val producerRecord: ProducerRecord<String, String> = ProducerRecord(it.sendToTopic, it.messageKey, it.eventBodyJson)
            val senderRecord: SenderRecord<String, String, String> = SenderRecord.create(producerRecord, it.eventId)
            reactiveKafkaProducerTemplate.send(senderRecord).doOnSuccess{ log.info("topic=${it.recordMetadata().topic()} partition=${it.recordMetadata().partition()}")}
        }.flatMap { deleteById(it.correlationMetadata()) }.`as`(transactionalOperator::transactional).subscribe()
    }

    abstract fun getMessage(): Flux<OutboxEvent>

    abstract fun deleteById(id: String): Mono<Void>
}