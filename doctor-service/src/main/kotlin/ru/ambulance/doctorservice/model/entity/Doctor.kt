package ru.ambulance.doctorservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("doctor")
data class Doctor (
    @Id @Column("id") val id: String,
    val lastName: String,
    val firstName: String,
    val patronymicName: String,
    val specialization: String,
    val hospitalId: String,
    val userId: String
)