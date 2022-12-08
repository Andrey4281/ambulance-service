package ru.ambulance.nurseservice.service

import reactor.core.publisher.Mono

interface NurseShiftService {

    fun beginShift(nurseId: String, tZone: String): Mono<String>

    fun endShift(nurseId: String): Mono<Void>
}