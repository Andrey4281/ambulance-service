package ru.ambulance.broker.events.appeal

import ru.ambulance.broker.events.base.SagaResponse
import java.util.*

/**
 * Ответ на событие CreatingAppealEvent со стороны DoctorService(сага createAppeal)
 */
data class DoctorResponseOnCreatingAppealEvent(
        /**
         * Идентификатор доктора
         */
        var doctorId: String?,
        override var isSuccess: Boolean,
        override var eventId: String
) : SagaResponse(isSuccess, eventId)