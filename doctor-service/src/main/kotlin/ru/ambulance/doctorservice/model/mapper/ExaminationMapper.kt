package ru.ambulance.doctorservice.model.mapper

import ru.ambulance.doctorservice.model.entity.Examination
import ru.ambulance.doctorservice.model.rdto.ExaminationRdto
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.UUID

fun ExaminationRdto.toExamination(): Examination = Examination(
        examinationId = UUID.randomUUID().toString(),
        preliminaryDiagnosis = preliminaryDiagnosis,
        currentPatientState = currentPatientState.name,
        examinationTime = OffsetDateTime.now(ZoneId.of("UTC")).toLocalDateTime(),
        tZone = tZone,
        appealId = appealId,
        doctorId = doctorId
)