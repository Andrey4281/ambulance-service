package ru.ambulance.doctorservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("treatment_assignment")
data class TreatmentAssignment(
        @Id @Column("id") val id: String,
        val examinationId: String,
        val treatmentKindId: String,
        val isExecuted: Boolean = false
)