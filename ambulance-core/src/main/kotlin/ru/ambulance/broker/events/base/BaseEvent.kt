package ru.ambulance.broker.events.base

import java.util.*

/**
 * Базовый класс события
 */
abstract class BaseEvent(
        /**
         * Уникальный идентификатор события. Служит ключом идемпотентности.
         */
        open var eventId: UUID
)