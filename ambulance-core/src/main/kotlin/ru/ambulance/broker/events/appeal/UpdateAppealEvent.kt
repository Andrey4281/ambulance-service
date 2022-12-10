package ru.ambulance.broker.events.appeal

import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.enums.AppealStatus

data class UpdateAppealEvent(
        var appealId: String,
        var appealStatus: AppealStatus,
        override var eventId: String
): BaseEvent(eventId)