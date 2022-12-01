package ru.ambulance.broker.events.base

import java.util.*

abstract class SagaResponse(
        /**
         * Признак успеха операции
         */
        open var isSuccess: Boolean = true, override var eventId: UUID): BaseEvent(eventId)