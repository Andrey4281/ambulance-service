package ru.ambulance.doctorservice.service

import reactor.core.publisher.Mono

interface DoctorShiftService {

    fun beginShift(doctorId: String, tZone: String): Mono<String>

    fun endShift(doctorId: String): Mono<Void>
}