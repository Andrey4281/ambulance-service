package ru.ambulance.doctorservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime

@Table("doctor_shift")
data class DoctorShift(
        @Id @Column("id") val doctorShiftId: String,
        val doctorId: String,
        var isActive: Boolean = true,
        val date: OffsetDateTime,
        @Column("tzone") val tZone: String,
        var activeAppealCount: Int = 0,
        var totalAppealCount: Int = 0,
        @org.springframework.data.annotation.Transient var isNewObject: Boolean = true
) : Persistable<String> {

    @PersistenceConstructor
    constructor(
            doctorShiftId: String,
            doctorId: String,
            isActive: Boolean,
            date: OffsetDateTime,
            tZone: String,
            activeAppealCount: Int,
            totalAppealCount: Int
    ) : this(doctorShiftId = doctorShiftId,
            doctorId = doctorId,
            isActive = isActive,
            date = date,
            tZone = tZone,
            activeAppealCount = activeAppealCount,
            totalAppealCount = totalAppealCount,
            isNewObject = true)

    override fun getId(): String = doctorShiftId

    override fun isNew(): Boolean = isNewObject
}