package ru.ambulance.nurseservice.model.rdto

data class InvestigationResultRdto(
        val investigationResultId: String,
        val isExecuted: Boolean?,
        val filePath: String?
)