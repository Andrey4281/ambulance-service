package ru.ambulance.doctorservice.service

import org.springframework.data.repository.query.Param
import reactor.core.publisher.Mono

interface DoctorService {
    fun findRequiredDoctorWithMinActiveAppeal(hospitalId: String, specialization: String): Mono<String>
}