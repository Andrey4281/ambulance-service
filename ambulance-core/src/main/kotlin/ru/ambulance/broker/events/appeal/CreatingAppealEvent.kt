package ru.ambulance.broker.events.appeal

import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.enums.DoctorSpecialization
import java.util.*

/**
 * Событие назначение обращения на доктора
 */
data class CreatingAppealEvent(
        /**
         * Идентификатор обращения
         */
        var appealId: Long,
        /**
         * Требуемая специализация доктора для первичного осмотра
         */
        var primaryRequiredDoctor: DoctorSpecialization,
        override var eventId: UUID
): BaseEvent(eventId)