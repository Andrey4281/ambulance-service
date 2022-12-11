package ru.ambulance.doctorservice.model.rdto

import ru.ambulance.enums.PatientState

data class ExaminationRdto(
        val preliminaryDiagnosis: String,
        val currentPatientState: PatientState,
        val tZone: String,
        val appealId: String,
        val doctorId: String,
        val hospitalId: String,
        val requiredInvestigations: List<String>,
        val requiredTreatments: List<String>
)