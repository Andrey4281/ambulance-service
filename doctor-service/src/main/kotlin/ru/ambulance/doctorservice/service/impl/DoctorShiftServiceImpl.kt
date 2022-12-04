package ru.ambulance.doctorservice.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.doctorservice.dao.DoctorShiftRepository
import ru.ambulance.doctorservice.model.entity.DoctorShift
import ru.ambulance.doctorservice.model.entity.exceptions.ActiveShiftAlreadyExistsException
import ru.ambulance.doctorservice.service.DoctorShiftService
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

@Service
class DoctorShiftServiceImpl(private val doctorShiftRepository: DoctorShiftRepository): DoctorShiftService {

    //TODO asemenov webflux обработку исключения
    override fun beginShift(doctorId: String, tZone: String): Mono<String> {
        return doctorShiftRepository.isExistActiveDoctorShift(doctorId).flatMap {
            if (it) {
                Mono.error(ActiveShiftAlreadyExistsException("Active shift already exists for this doctor"))
            } else {
                val shift = DoctorShift(doctorShiftId = UUID.randomUUID().toString(),
                doctorId = doctorId, date = OffsetDateTime.now(ZoneId.of("UTC")), tZone = tZone)
                doctorShiftRepository.save(shift)
            }
        }.map { it.doctorShiftId }
    }

    override fun endShift(doctorId: String): Mono<Void> = doctorShiftRepository.endShift(doctorId)
}