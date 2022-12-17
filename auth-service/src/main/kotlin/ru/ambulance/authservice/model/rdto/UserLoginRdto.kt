package ru.ambulance.authservice.model.rdto

data class UserLoginRdto(
        val login: String,
        val password: String
)