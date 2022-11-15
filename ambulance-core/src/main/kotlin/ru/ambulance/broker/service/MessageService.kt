package ru.ambulance.broker.service

import com.fasterxml.jackson.databind.ObjectMapper
import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.broker.outbox.OutboxEvent
import java.util.UUID

abstract class MessageService<T : BaseEvent>(private val objectMapper: ObjectMapper) {

    //TODO asemenov возможно Mono возвращать
    fun sendMessage(messageKey: String?,
                    sendToTopic: String, event: T) {

        event.eventId = UUID.randomUUID()
        val outboxEvent = OutboxEvent(messageKey, objectMapper.writeValueAsString(event), sendToTopic)
        saveMessageToDb(outboxEvent)
    }

    abstract fun saveMessageToDb(outboxEvent: OutboxEvent)
}