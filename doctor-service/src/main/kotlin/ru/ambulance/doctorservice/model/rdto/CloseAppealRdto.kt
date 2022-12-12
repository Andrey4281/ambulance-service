package ru.ambulance.doctorservice.model.rdto

import ru.ambulance.enums.AppealStatus

data class CloseAppealRdto(
        val appealId: String,
        val doctorId: String,
        val appealStatus: AppealStatus
)