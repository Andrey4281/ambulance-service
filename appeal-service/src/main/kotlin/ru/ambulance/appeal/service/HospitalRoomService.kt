package ru.ambulance.appeal.service

import reactor.core.publisher.Mono

interface HospitalRoomService {
    fun findHospitalRoomNumberByType(type: String) : Mono<Int>
}