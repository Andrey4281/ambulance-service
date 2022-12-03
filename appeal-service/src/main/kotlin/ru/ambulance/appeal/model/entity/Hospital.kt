package ru.ambulance.appeal.model.entity

import org.springframework.data.annotation.Id

data class Hospital(
        @Id val id: String,
        val name: String,
        val address: String
)