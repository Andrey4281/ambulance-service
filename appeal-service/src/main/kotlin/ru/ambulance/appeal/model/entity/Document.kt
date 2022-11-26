package ru.ambulance.appeal.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.Date

@Table("document")
data class Document(
        @Id
        val id: String,
        val series: String,
        val number: String,
        val issuedBy: String,
        val issuedDate: Date,
        val expirationDate: Date
)