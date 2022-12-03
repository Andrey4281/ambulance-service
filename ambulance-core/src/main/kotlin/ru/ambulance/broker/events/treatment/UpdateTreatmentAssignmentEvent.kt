package ru.ambulance.broker.events.treatment

import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.enums.update.TreatmentAssignmentFields
import java.util.*

data class UpdateTreatmentAssignmentEvent(
        var examinationId: Long,
        var treatmentKindId: Long,
        var isExecuted: Boolean = false,
        var updatedFields: List<TreatmentAssignmentFields> = Collections.emptyList(),
        override var eventId: String
): BaseEvent(eventId)