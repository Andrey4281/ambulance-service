package ru.ambulance.nurseservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("investigation_result")
data class InvestigationResult(
        @Id
        @Column("id") val investigationResultId: String,
        val examinationId: String,
        val appealId: String,
        var filePath: String?,
        val investigationKindId: String,
        var isExecuted: Boolean = false,
        val nurseId: String?,
        @org.springframework.data.annotation.Transient var isNewObject: Boolean = true
) : Persistable<String> {

        @PersistenceConstructor
        constructor(
                investigationResultId: String,
                examinationId: String,
                appealId: String,
                filePath: String?,
                investigationKindId: String,
                isExecuted: Boolean,
                nurseId: String?)
                : this(investigationResultId = investigationResultId,
                examinationId = examinationId,
                appealId = appealId,
                filePath = filePath,
                investigationKindId = investigationKindId,
                isExecuted = isExecuted,
                nurseId = nurseId,
                isNewObject = true)

        override fun getId(): String = investigationResultId

        override fun isNew(): Boolean = isNewObject
}