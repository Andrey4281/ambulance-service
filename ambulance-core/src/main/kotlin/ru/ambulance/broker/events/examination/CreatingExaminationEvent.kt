package ru.ambulance.broker.events.examination

import ru.ambulance.broker.events.base.BaseEvent
import java.util.*

/**
 * Событие создания доктором листа осмотра пациента
 */
data class CreatingExaminationEvent(
        /**
         * Идентификатор обращения пациента
         */
        var appealId: String,
        /**
         * Идентификатор листа осмотра пациента
         */
        var examinationId: String,
        /**
         * Идентификатор больницы где необходимо провести анализы и лечение
         */
        var hospitalId: String,
        /**
         * Список идентификаторов видов анализов, необходимых для лечения пациента
         */
        var investigationKindIds: List<String> = Collections.emptyList(),
        /**
         * Список необходимых видов лечения для пациента
         */
        var treatmentKindIds: List<String> = Collections.emptyList(),
        override var eventId: String
): BaseEvent(eventId)