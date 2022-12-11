package ru.ambulance.nurseservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("treatment_result")
data class TreatmentResult(
        @Id
        @Column("id") val treatmentResultId: String,
        val examinationId: String,
        val appealId: String,
        val treatmentKindId: String,
        var isExecuted: Boolean = false,
        val nurseId: String?,
        @org.springframework.data.annotation.Transient var isNewObject: Boolean = true
) : Persistable<String> {

        @PersistenceConstructor
        constructor(
                treatmentResultId: String,
                examinationId: String,
                appealId: String,
                treatmentKindId: String,
                isExecuted: Boolean,
                nurseId: String?)
                : this(treatmentResultId = treatmentResultId,
                examinationId = examinationId,
                appealId = appealId,
                treatmentKindId = treatmentKindId,
                isExecuted = isExecuted,
                nurseId = nurseId,
                isNewObject = true)

        override fun getId(): String = treatmentResultId

        override fun isNew(): Boolean = isNewObject
}