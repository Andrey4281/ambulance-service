package ru.ambulance.broker.events.examination

import ru.ambulance.broker.events.base.BaseEvent
import java.util.*

/**
 * Событие создания доктором листа осмотра пациента
 */
data class CreatingExaminationEvent(
        /**
         * Идентификатор листа осмотра пациента
         */
        var examinationId: Long,
        /**
         * Идентификатор больницы где необходимо провести анализы и лечение
         */
        var hospitalId: Long?,
        /**
         * Список идентификаторов видов анализов, необходимых для лечения пациента
         */
        var investigationKindIds: List<Long> = Collections.emptyList(),
        /**
         * Список необходимых видов лечения для пациента
         */
        var treatmentKindIds: List<Long> = Collections.emptyList(), override var eventId: UUID
): BaseEvent(eventId)