package ru.ambulance.broker.events.investigation

import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.enums.update.InvestigationAssignmentFields
import java.util.*

data class UpdateInvestigationAssignmentEvent(
        var examinationId: Long,
        var investigationKindId: Long,
        var isExecuted: Boolean = false,
        var isReady: Boolean = false,
        var updatedFields: List<InvestigationAssignmentFields> = Collections.emptyList(),
        override var eventId: UUID
): BaseEvent(eventId)