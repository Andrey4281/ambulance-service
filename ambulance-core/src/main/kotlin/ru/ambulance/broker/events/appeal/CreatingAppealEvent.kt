package ru.ambulance.broker.events.appeal

import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.enums.DoctorSpecialization

/**
 * Событие назначение обращения на доктора
 */
data class CreatingAppealEvent(
        /**
         * Требуемая специализация доктора для первичного осмотра
         */
        var primaryRequiredDoctor: DoctorSpecialization,
        var hospitalId: String,
        var appealId: String,
        override var eventId: String
): BaseEvent(eventId)