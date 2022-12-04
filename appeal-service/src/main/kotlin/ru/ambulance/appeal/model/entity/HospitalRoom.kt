package ru.ambulance.appeal.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class HospitalRoom(
        @Id val id: String,
        val type: String,
        val hospitalId: String,
        val cabinetNumber: Int
)