package ru.ambulance.appeal.model.dto

import ru.ambulance.enums.AppealStatus
import ru.ambulance.enums.DoctorSpecialization
import ru.ambulance.enums.PatientState

data class AppealDto (
    val id: String, //  UUID identifier appeal
    val authorId: String, //  UUID reference to userId
    val description: String,
    val primaryPatientStatus: PatientState,
    val patientId: String, //  UUID identifier of patient
    val currentDoctorId: String, //  UUID identifier of doctor
    val primaryRequiredDoctor: DoctorSpecialization,
    val hospitald: String, //  UUID identifier of hospital

    // optional and rewritable fields
    var appealStatus: AppealStatus = AppealStatus.NEW,
    var currentCabinetNumber: Long
)