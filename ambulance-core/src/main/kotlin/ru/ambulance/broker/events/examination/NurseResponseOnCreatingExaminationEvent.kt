package ru.ambulance.broker.events.examination

import ru.ambulance.broker.events.base.SagaResponse
import java.util.*

/**
 * Ответ на событие CreatingExaminationEvent со стороны NurseService(сага createExamination)
 */
data class NurseResponseOnCreatingExaminationEvent(override var isSuccess: Boolean, override var eventId: String)
    : SagaResponse(isSuccess, eventId)