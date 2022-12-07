package ru.ambulance.nurseservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("nurse")
data class Nurse(
        @Id
        @Column("id") val id: String,
        val firstName: String,
        val lastName: String,
        val patronymicName: String,
        val userId: String,
        val hospitalId: String
)