package ru.ambulance.appeal.controller.mapper

import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

fun toResponse(it: Any) = ServerResponse.ok().body(BodyInserters.fromValue(it))