package ru.ambulance.nurseservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("id")
data class TreatmentResult(
        @Id
        @Column("id") val id: String,
        val examinationId: String,
        val appealId: String,
        val treatmentKindId: String,
        var isExecuted: Boolean = false,
        val nurseId: String
)