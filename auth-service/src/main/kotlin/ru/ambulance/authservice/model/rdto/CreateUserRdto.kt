package ru.ambulance.authservice.model.rdto

data class CreateUserRdto(
        val login: String,
        val phone: String,
        val password: String,
        val email: String,
        val roleId: String
)