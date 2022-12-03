package ru.ambulance.appeal.broker.outbox

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.appeal.dao.OutboxEventRepository
import ru.ambulance.broker.events.appeal.CreatingAppealEvent
import ru.ambulance.broker.events.outbox.OutboxEvent
import ru.ambulance.broker.service.MessageService

@Service
class AppealMessageServiceImpl(private val objectMapper: ObjectMapper, private val outboxEventRepository: OutboxEventRepository) : MessageService<CreatingAppealEvent>(objectMapper) {

    override fun saveMessageToDb(outboxEvent: OutboxEvent): Mono<OutboxEvent> = outboxEventRepository.save(outboxEvent)
}