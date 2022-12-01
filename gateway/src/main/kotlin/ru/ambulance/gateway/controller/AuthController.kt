package ru.ambulance.gateway.controller


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono


@RestController
class AuthController(val webClient: WebClient) {

    @GetMapping("/token")
    fun token(): Mono<String> = webClient
        .get()
        .uri("token_url")
        .exchangeToMono{ it.bodyToMono(String::class.java) }

    @GetMapping("/")
    fun index(session: WebSession): Mono<String> = Mono.just(session.getId())


}