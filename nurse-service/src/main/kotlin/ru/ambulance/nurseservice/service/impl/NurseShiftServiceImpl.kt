package ru.ambulance.nurseservice.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import ru.ambulance.model.exceptions.ActiveShiftAlreadyExistsException
import ru.ambulance.nurseservice.dao.NurseShiftRepository
import ru.ambulance.nurseservice.model.entity.NurseShift
import ru.ambulance.nurseservice.service.NurseShiftService
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

@Service
class NurseShiftServiceImpl(private val nurseShiftRepository: NurseShiftRepository): NurseShiftService {

    @Transactional
    override fun beginShift(nurseId: String, tZone: String): Mono<String> {
        return nurseShiftRepository.isExistActiveNurseShift(nurseId).flatMap {
            if (it) {
                Mono.error(ActiveShiftAlreadyExistsException("Active shift already exists for this nurse"))
            } else {
                val shift = NurseShift(nurseShiftId = UUID.randomUUID().toString(),
                        nurseId = nurseId, date = OffsetDateTime.now(ZoneId.of("UTC")).toLocalDateTime(), tZone = tZone)
                nurseShiftRepository.save(shift)
            }
        }.map { it.nurseShiftId }
    }

    override fun endShift(nurseId: String): Mono<Void> = nurseShiftRepository.endShift(nurseId)
}