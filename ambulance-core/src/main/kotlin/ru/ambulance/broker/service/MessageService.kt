package ru.ambulance.broker.service

import com.fasterxml.jackson.databind.ObjectMapper
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.broker.events.outbox.OutboxEvent
import java.util.UUID

abstract class MessageService<T : BaseEvent>(private val objectMapper: ObjectMapper) {

    fun sendMessage(messageKey: String?,
                    sendToTopic: String, event: T): Mono<OutboxEvent> {

        event.eventId = UUID.randomUUID()
        val outboxEvent = OutboxEvent(event.eventId, messageKey, objectMapper.writeValueAsString(event), sendToTopic)
        return saveMessageToDb(outboxEvent)
    }

    abstract fun saveMessageToDb(outboxEvent: OutboxEvent): Mono<OutboxEvent>
}