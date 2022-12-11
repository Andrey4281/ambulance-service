package ru.ambulance.doctorservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("examination")
data class Examination(
        @Id @Column("id") val examinationId: String,
        val preliminaryDiagnosis: String,
        val currentPatientState: String,
        val examinationTime: LocalDateTime,
        @Column("tzone") val tZone: String,
        val appealId: String,
        val doctorId: String,
        @org.springframework.data.annotation.Transient var isNewObject: Boolean = true
) : Persistable<String> {

    @PersistenceConstructor
    constructor(
            examinationId: String,
            preliminaryDiagnosis: String,
            currentPatientState: String,
            examinationTime: LocalDateTime,
            tZone: String,
            appealId: String,
            doctorId: String
    ) : this(examinationId = examinationId,
            preliminaryDiagnosis = preliminaryDiagnosis,
            currentPatientState = currentPatientState,
            examinationTime = examinationTime,
            tZone = tZone,
            appealId = appealId,
            doctorId = doctorId,
            isNewObject = true)

    override fun getId(): String = examinationId

    override fun isNew(): Boolean = isNewObject
}