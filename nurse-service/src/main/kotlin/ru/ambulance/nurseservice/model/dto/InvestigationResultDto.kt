package ru.ambulance.nurseservice.model.dto

data class InvestigationResultDto(
    val investigationResultId: String,
    val examinationId: String,
    val appealId: String,
    var filePath: String?,
    val investigationKindId: String,
    var isExecuted: Boolean,
    val nurseId: String?
)