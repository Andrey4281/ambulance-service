package ru.ambulance.appeal.broker.outbox

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.appeal.dao.OutboxEventRepository
import ru.ambulance.broker.events.outbox.OutboxEvent
import ru.ambulance.broker.service.OutboxProcessor

@Service
class AppealOutboxProcessor(private val outboxEventRepository: OutboxEventRepository) : OutboxProcessor() {

    override fun getMessage(): Flux<OutboxEvent> = outboxEventRepository.findAllWithLock()

    override fun deleteById(id: String): Mono<Void> = outboxEventRepository.deleteById(id)
}