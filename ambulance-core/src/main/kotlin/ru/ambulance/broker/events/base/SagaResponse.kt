package ru.ambulance.broker.events.base

abstract class SagaResponse(
        /**
         * Признак успеха операции
         */
        open var isSuccess: Boolean = true)