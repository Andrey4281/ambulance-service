package ru.ambulance.doctorservice.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.appeal.UpdateAppealEvent
import ru.ambulance.doctorservice.broker.outbox.DoctorMessageServiceImpl
import ru.ambulance.doctorservice.dao.DoctorShiftRepository
import ru.ambulance.doctorservice.model.entity.DoctorShift
import ru.ambulance.doctorservice.model.rdto.CloseAppealRdto
import ru.ambulance.doctorservice.service.DoctorShiftService
import ru.ambulance.enums.AppealStatus
import ru.ambulance.model.exceptions.ActiveShiftAlreadyExistsException
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

@Service
class DoctorShiftServiceImpl(private val doctorShiftRepository: DoctorShiftRepository,
                             private val doctorMessageService: DoctorMessageServiceImpl): DoctorShiftService {


    @Value("\${kafka.topics.appealCommandTopic}")
    private val appealCommandTopic: String = "appealCommandTopic"

    //TODO asemenov webflux обработку исключения
    //TODO asemenov могут быть проблемы если две параллельные транзакции
    @Transactional
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

    @Transactional
    override fun closeAppeal(closeAppealRdto: CloseAppealRdto): Mono<String> {
        return findActiveShiftByDoctorId(closeAppealRdto.doctorId).flatMap {
            it.activeAppealCount = it.activeAppealCount - 1
            updateShift(it)
        }.flatMap { doctorMessageService.sendMessage(messageKey = null, sendToTopic = appealCommandTopic,
                UpdateAppealEvent(appealId = closeAppealRdto.appealId, appealStatus = closeAppealRdto.appealStatus,
                        eventId = UUID.randomUUID().toString())) }.map { closeAppealRdto.appealId }
    }
}