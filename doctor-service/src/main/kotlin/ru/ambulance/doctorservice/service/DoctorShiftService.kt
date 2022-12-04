package ru.ambulance.doctorservice.service

import reactor.core.publisher.Mono
import ru.ambulance.doctorservice.model.entity.DoctorShift

interface DoctorShiftService {

    fun beginShift(doctorId: String, tZone: String): Mono<String>

    fun endShift(doctorId: String): Mono<Void>

    fun findActiveShiftByDoctorId(doctorId: String): Mono<DoctorShift>

    fun updateShift(doctorShift: DoctorShift): Mono<DoctorShift>
}