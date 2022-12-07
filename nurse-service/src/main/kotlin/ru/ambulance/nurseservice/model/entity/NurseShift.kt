package ru.ambulance.nurseservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("nurse_shift")
data class NurseShift(
        @Id
        @Column("id") val nurseShiftId: String,
        val nurseId: String,
        var isActive: Boolean = true,
        val date: LocalDateTime,
        @Column("tzone") val tZone: String,
        var activeInvestigationCount: Int = 0,
        var totalInvestigationCount: Int = 0,
        var activeTreatmentCount: Int = 0,
        var totalTreatmentCount: Int = 0
)