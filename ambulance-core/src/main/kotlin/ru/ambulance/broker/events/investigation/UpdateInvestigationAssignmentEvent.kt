package ru.ambulance.broker.events.investigation

import ru.ambulance.enums.update.InvestigationAssignmentFields
import java.util.Collections

data class UpdateInvestigationAssignmentEvent(
        var examinationId: Long,
        var investigationKindId: Long,
        var isExecuted: Boolean = false,
        var isReady: Boolean = false,
        var updatedFields: List<InvestigationAssignmentFields> = Collections.emptyList())