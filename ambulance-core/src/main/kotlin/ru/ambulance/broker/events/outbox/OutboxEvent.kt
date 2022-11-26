package ru.ambulance.broker.events.outbox

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.domain.Persistable

//TODO https://stackoverflow.com/questions/65528934/spring-data-jdbc-kotlin-support-required-property-not-found-for-class
/**
 * Базовая сущность для сохранения в БД событий для реализации транзакционного обмена сообщениями.
 * Паттерн outbox
 */
data class OutboxEvent(

        @Id var eventId: String?,
        /**
         * Ключ сообщения
         */
        var messageKey: String?,
        /**
         * Представление события в формате json
         */
        var eventBodyJson: String,
        /**
         * Наименование топика, куда следует отправить событие
         */
        var sendToTopic: String,

        @org.springframework.data.annotation.Transient var isNewObject: Boolean = true
) : Persistable<String> {

        @PersistenceConstructor
        constructor(
                eventId: String?,
                messageKey: String?,
                eventBodyJson: String,
                sendToTopic: String
        ) : this(eventId = eventId, messageKey = messageKey, eventBodyJson = eventBodyJson, sendToTopic = sendToTopic, isNewObject = true)

        override fun getId(): String? = eventId

        override fun isNew(): Boolean = isNewObject || eventId == null
}