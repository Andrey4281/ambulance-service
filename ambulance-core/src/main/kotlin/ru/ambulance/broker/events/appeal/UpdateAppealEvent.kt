package ru.ambulance.broker.events.appeal

import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.enums.AppealStatus
import ru.ambulance.enums.update.AppealFields
import java.util.*

data class UpdateAppealEvent(
        var appealId: Long,
        var appealStatus: AppealStatus,
        var updatedFields: List<AppealFields> = Collections.emptyList(),
        override var eventId: UUID
): BaseEvent(eventId)