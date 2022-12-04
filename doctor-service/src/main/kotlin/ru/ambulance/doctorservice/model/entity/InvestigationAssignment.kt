package ru.ambulance.doctorservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("investigation_assignment")
data class InvestigationAssignment(
        @Id @Column("id") val id: String,
        val examinationId: String,
        val investigationKindId: String,
        val isExecuted: Boolean = false,
        val isReady: Boolean = false
)