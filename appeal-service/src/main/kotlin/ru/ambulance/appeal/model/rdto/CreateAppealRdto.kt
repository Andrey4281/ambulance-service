package ru.ambulance.appeal.model.rdto

import ru.ambulance.enums.DoctorSpecialization
import ru.ambulance.enums.HospitalRoomType
import ru.ambulance.enums.PatientState

data class CreateAppealRdto (
        val authorId: String,
        val description: String,
        val primaryPatientStatus: PatientState,
        val patientId: String?,
        val primaryRequiredDoctor: DoctorSpecialization,
        val hospitalId: String,
        val hospitalRoomType: HospitalRoomType?
)