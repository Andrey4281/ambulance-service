package ru.ambulance.nurseservice.broker.outbox

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.outbox.OutboxEvent
import ru.ambulance.broker.service.MessageService
import ru.ambulance.nurseservice.dao.OutboxEventRepository

@Service
class NurseMessageServiceImpl(private val objectMapper: ObjectMapper,
                              private val outboxEventRepository: OutboxEventRepository) : MessageService(objectMapper) {

    override fun saveMessageToDb(outboxEvent: OutboxEvent): Mono<OutboxEvent> = outboxEventRepository.save(outboxEvent)
}