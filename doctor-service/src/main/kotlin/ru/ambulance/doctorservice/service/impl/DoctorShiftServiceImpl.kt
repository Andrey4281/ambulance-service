package ru.ambulance.doctorservice.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.doctorservice.dao.DoctorShiftRepository
import ru.ambulance.doctorservice.model.entity.DoctorShift
import ru.ambulance.doctorservice.model.exceptions.ActiveShiftAlreadyExistsException
import ru.ambulance.doctorservice.service.DoctorShiftService
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

@Service
class DoctorShiftServiceImpl(private val doctorShiftRepository: DoctorShiftRepository): DoctorShiftService {

    //TODO asemenov webflux обработку исключения
    //TODO asemenov могут быть проблемы если две параллельные транзакции
    override fun beginShift(doctorId: String, tZone: String): Mono<String> {
        return doctorShiftRepository.isExistActiveDoctorShift(doctorId).flatMap {
            if (it) {
                Mono.error(ActiveShiftAlreadyExistsException("Active shift already exists for this doctor"))
            } else {
                val shift = DoctorShift(doctorShiftId = UUID.randomUUID().toString(),
                doctorId = doctorId, date = OffsetDateTime.now(ZoneId.of("UTC")).toLocalDateTime(), tZone = tZone)
                doctorShiftRepository.save(shift)
            }
        }.map { it.doctorShiftId }
    }

    override fun endShift(doctorId: String): Mono<Void> = doctorShiftRepository.endShift(doctorId)

    override fun findActiveShiftByDoctorId(doctorId: String): Mono<DoctorShift> = doctorShiftRepository.findFirstByDoctorIdAndIsActiveTrue(doctorId)

    override fun updateShift(doctorShift: DoctorShift): Mono<DoctorShift> {
        doctorShift.isNewObject = false
        return doctorShiftRepository.save(doctorShift)
    }
}