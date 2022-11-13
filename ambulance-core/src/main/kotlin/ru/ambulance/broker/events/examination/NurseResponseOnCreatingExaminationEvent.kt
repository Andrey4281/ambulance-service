package ru.ambulance.broker.events.examination

import ru.ambulance.broker.events.base.SagaResponse

/**
 * Ответ на событие CreatingExaminationEvent со стороны NurseService(сага createExamination)
 */
data class NurseResponseOnCreatingExaminationEvent(override var isSuccess: Boolean) : SagaResponse(isSuccess)