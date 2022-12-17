package ru.ambulance.authservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("role")
data class Role(
        @Id val id: String,
        val name: String
)