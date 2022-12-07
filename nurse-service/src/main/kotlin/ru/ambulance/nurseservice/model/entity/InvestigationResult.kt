package ru.ambulance.nurseservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("investigation_result")
data class InvestigationResult(
        @Id
        @Column("id") val id: String,
        val examinationId: String,
        var filePath: String?,
        val investigationKindId: String,
        var isExecuted: Boolean = false,
        val nurseId: String
)