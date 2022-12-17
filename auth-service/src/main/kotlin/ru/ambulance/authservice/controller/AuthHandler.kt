package ru.ambulance.authservice.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import ru.ambulance.authservice.model.dto.UserLoginResponse
import ru.ambulance.authservice.model.rdto.CreateUserRdto
import ru.ambulance.authservice.model.rdto.UserLoginRdto
import ru.ambulance.authservice.service.AuthUserService

@Component
class AuthHandler(private val userService: AuthUserService) {

    fun signUp(request: ServerRequest): Mono<ServerResponse> {
        val createUserRdto: Mono<CreateUserRdto> = request.bodyToMono(CreateUserRdto::class.java)
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(createUserRdto.flatMap(userService::signUp),
                        String::class.java))
    }

    fun signIn(request: ServerRequest): Mono<ServerResponse> {
        val userLoginRdto: Mono<UserLoginRdto> = request.bodyToMono(UserLoginRdto::class.java)
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(userLoginRdto.flatMap(userService::signIn),
                        UserLoginResponse::class.java))
    }
}