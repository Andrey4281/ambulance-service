package ru.ambulance.doctorservice.broker.outbox

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.outbox.OutboxEvent
import ru.ambulance.broker.service.OutboxProcessor
import ru.ambulance.doctorservice.dao.OutboxEventRepository

@Service
class DoctorOutboxProcessor(private val outboxEventRepository: OutboxEventRepository) : OutboxProcessor() {

    override fun getMessage(): Flux<OutboxEvent> = outboxEventRepository.findAll()

    override fun deleteById(id: String): Mono<Void> = outboxEventRepository.deleteById(id)
}