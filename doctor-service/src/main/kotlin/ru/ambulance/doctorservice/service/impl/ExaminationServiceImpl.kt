package ru.ambulance.doctorservice.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.appeal.UpdateAppealEvent
import ru.ambulance.broker.events.examination.CreatingExaminationEvent
import ru.ambulance.doctorservice.broker.outbox.DoctorMessageServiceImpl
import ru.ambulance.doctorservice.dao.ExaminationRepository
import ru.ambulance.doctorservice.model.mapper.toExamination
import ru.ambulance.doctorservice.model.rdto.ExaminationRdto
import ru.ambulance.doctorservice.service.DoctorShiftService
import ru.ambulance.doctorservice.service.ExaminationService
import ru.ambulance.enums.AppealStatus
import java.util.UUID

@Service
class ExaminationServiceImpl(private val examinationRepository: ExaminationRepository,
                             private val doctorMessageService: DoctorMessageServiceImpl,
                             private val doctorShiftService: DoctorShiftService) : ExaminationService {

    @Value("\${kafka.topics.examinationRequestTopic}")
    private val examinationRequestTopic: String = "examinationRequestTopic"

    @Value("\${kafka.topics.appealCommandTopic}")
    private val appealCommandTopic: String = "appealCommandTopic"

    //TODO число активных обращений уменьшить на 1
    @Transactional
    override fun createNewExamination(examinationRdto: ExaminationRdto): Mono<String> {
        val examination = examinationRdto.toExamination()
        return examinationRepository.save(examination).flatMap {
            if (examinationRdto.requiredInvestigations.isEmpty()
                    && examinationRdto.requiredTreatments.isEmpty()) {
                val updateAppealEvent = UpdateAppealEvent(appealId = it.appealId, appealStatus = AppealStatus.RELEASED,
                        eventId = UUID.randomUUID().toString())
                doctorShiftService.findActiveShiftByDoctorId(examinationRdto.doctorId)
                        .flatMap {
                            it.activeAppealCount = it.activeAppealCount - 1
                            doctorShiftService.updateShift(it)
                        }.then(doctorMessageService.sendMessage(null, appealCommandTopic, updateAppealEvent))
            } else {
                val creatingExaminationEvent = CreatingExaminationEvent(
                        appealId = it.appealId,
                        examinationId = it.examinationId,
                        hospitalId = examinationRdto.hospitalId,
                        investigationKindIds = examinationRdto.requiredInvestigations,
                        treatmentKindIds = examinationRdto.requiredTreatments,
                        eventId = UUID.randomUUID().toString()
                )
                doctorMessageService.sendMessage(null, examinationRequestTopic, creatingExaminationEvent)
            }
        }.map { examination.examinationId }
    }
}