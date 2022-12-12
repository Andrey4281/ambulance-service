package ru.ambulance.nurseservice.model.mapper

import ru.ambulance.nurseservice.model.dto.InvestigationResultDto
import ru.ambulance.nurseservice.model.dto.TreatmentResultDto
import ru.ambulance.nurseservice.model.entity.InvestigationResult
import ru.ambulance.nurseservice.model.entity.TreatmentResult

fun InvestigationResult.toDto(): InvestigationResultDto = InvestigationResultDto(
        investigationResultId = investigationResultId,
        examinationId = examinationId,
        appealId = appealId,
        filePath = filePath,
        investigationKindId = investigationKindId,
        isExecuted = isExecuted,
        nurseId = nurseId
)

fun TreatmentResult.toDto(): TreatmentResultDto = TreatmentResultDto(
        treatmentResultId = treatmentResultId,
        examinationId = examinationId,
        appealId = appealId,
        treatmentKindId = treatmentKindId,
        isExecuted = isExecuted,
        nurseId = nurseId
)