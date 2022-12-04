package ru.ambulance.doctorservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime

@Table("doctor_shift")
data class DoctorShift(
        @Id @Column("id") val id: String,
        val doctorId: String,
        val isActive: Boolean = true,
        val date: OffsetDateTime,
        @Column("tzone") val tZone: String,
        val activeAppealCount: Int = 0,
        val totalAppealCount: Int = 0
)