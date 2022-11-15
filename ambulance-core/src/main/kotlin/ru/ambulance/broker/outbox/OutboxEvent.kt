package ru.ambulance.broker.outbox

import java.util.*

/**
 * Базовая сущность для сохранения в БД событий для реализации транзакционного обмена сообщениями.
 * Паттерн outbox
 */
data class OutboxEvent(
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
        var sendToTopic: String
)