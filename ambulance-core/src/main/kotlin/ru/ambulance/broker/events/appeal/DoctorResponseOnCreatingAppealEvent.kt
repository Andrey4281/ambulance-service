package ru.ambulance.broker.events.appeal

import ru.ambulance.broker.events.base.SagaResponse

/**
 * Ответ на событие CreatingAppealEvent со стороны DoctorService(сага createAppeal)
 */
data class DoctorResponseOnCreatingAppealEvent(
        /**
         * Идентификатор доктора
         */
        var doctorId: Long?,
        override var isSuccess: Boolean
) : SagaResponse(isSuccess)