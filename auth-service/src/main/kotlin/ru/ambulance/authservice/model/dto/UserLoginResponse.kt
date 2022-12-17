package ru.ambulance.authservice.model.dto

data class UserLoginResponse(
        val isSuccess: Boolean,
        val token: String?
)