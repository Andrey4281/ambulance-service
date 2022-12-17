package ru.ambulance.authservice.service

import reactor.core.publisher.Mono
import ru.ambulance.authservice.model.dto.UserLoginResponse
import ru.ambulance.authservice.model.rdto.CreateUserRdto
import ru.ambulance.authservice.model.rdto.UserLoginRdto

interface AuthUserService {
    fun signUp(userRdto: CreateUserRdto): Mono<String>

    fun signIn(userLoginRdto: UserLoginRdto): Mono<UserLoginResponse>
}