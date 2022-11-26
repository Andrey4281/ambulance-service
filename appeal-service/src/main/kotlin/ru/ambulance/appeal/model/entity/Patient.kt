package ru.ambulance.appeal.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table
data class Patient(
        @Id val id: String,
        val firstName: String,
        val lastName: String,
        val patronymic: String,
        val phone: String,
        val email: String,
        val address: String,
        val docTypeId: String,
        val userId: String,
        val gender: String,
        val birthDate: LocalDate
)