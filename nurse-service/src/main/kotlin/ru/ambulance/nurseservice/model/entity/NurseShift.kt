package ru.ambulance.nurseservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.annotation.Version
import org.springframework.data.domain.Persistable
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
        @Version var version: Long?,
        @Column("tzone") val tZone: String,
        var activeInvestigationCount: Int = 0,
        var totalInvestigationCount: Int = 0,
        var activeTreatmentCount: Int = 0,
        var totalTreatmentCount: Int = 0,
        @org.springframework.data.annotation.Transient var isNewObject: Boolean = true
) : Persistable<String> {

    @PersistenceConstructor
    constructor(
            nurseShiftId: String,
            nurseId: String,
            isActive: Boolean,
            date: LocalDateTime,
            tZone: String,
            version: Long?,
            activeInvestigationCount: Int,
            totalInvestigationCount: Int,
            activeTreatmentCount: Int,
            totalTreatmentCount: Int
    ) : this(nurseShiftId = nurseShiftId,
            nurseId = nurseId,
            isActive = isActive,
            date = date,
            tZone = tZone,
            version = version,
            activeInvestigationCount = activeInvestigationCount,
            totalInvestigationCount = totalInvestigationCount,
            activeTreatmentCount = activeTreatmentCount,
            totalTreatmentCount = totalTreatmentCount,
            isNewObject = true)

    override fun getId(): String = nurseShiftId

    override fun isNew(): Boolean = isNewObject
}