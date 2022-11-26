package ru.ambulance.broker.events.base

/**
 * Базовый класс события
 */
abstract class BaseEvent(
        /**
         * Уникальный идентификатор события. Служит ключом идемпотентности.
         */
        open var eventId: String
)