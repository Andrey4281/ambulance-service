package ru.ambulance.doctorservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime

@Table("examination")
data class Examination(
    @Id @Column("id") val id: String,
    val preliminaryDiagnosis: String,
    val currentPatientState: String,
    val examinationTime: OffsetDateTime,
    val tZone: String,
    val appealId: String,
    val doctorId: String
)