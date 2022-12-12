package ru.ambulance.nurseservice.service

import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.model.entity.NurseShift

interface NurseShiftService {
    fun beginShift(nurseId: String, tZone: String): Mono<String>

    fun endShift(nurseId: String): Mono<Void>

    fun findFirstByNurseIdAndIsActiveTrue(nurseId: String): Mono<NurseShift>

    fun updateNurseShift(nurseShift: NurseShift): Mono<NurseShift>
}