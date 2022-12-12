package ru.ambulance.nurseservice.model.dto

data class TreatmentResultDto(
        val treatmentResultId: String,
        val examinationId: String,
        val appealId: String,
        val treatmentKindId: String,
        var isExecuted: Boolean,
        val nurseId: String?
)