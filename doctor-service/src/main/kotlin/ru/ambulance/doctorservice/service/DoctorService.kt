package ru.ambulance.doctorservice.service

import reactor.core.publisher.Mono

interface DoctorService {
    fun findRequiredDoctorWithMinActiveAppeal(hospitalId: String, specialization: String): Mono<String>
}