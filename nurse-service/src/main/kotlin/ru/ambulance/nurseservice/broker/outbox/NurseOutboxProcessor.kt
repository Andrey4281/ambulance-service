package ru.ambulance.nurseservice.broker.outbox

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.outbox.OutboxEvent
import ru.ambulance.broker.service.OutboxProcessor
import ru.ambulance.nurseservice.dao.OutboxEventRepository

@Service
class NurseOutboxProcessor(private val outboxEventRepository: OutboxEventRepository) : OutboxProcessor() {

    override fun getMessage(): Flux<OutboxEvent> = outboxEventRepository.findAllWithLock()

    override fun deleteById(id: String): Mono<Void> = outboxEventRepository.deleteById(id)
}