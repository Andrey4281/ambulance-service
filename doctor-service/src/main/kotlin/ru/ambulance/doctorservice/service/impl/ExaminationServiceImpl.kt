package ru.ambulance.doctorservice.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.examination.CreatingExaminationEvent
import ru.ambulance.doctorservice.broker.outbox.DoctorMessageServiceImpl
import ru.ambulance.doctorservice.dao.ExaminationRepository
import ru.ambulance.doctorservice.model.mapper.toExamination
import ru.ambulance.doctorservice.model.rdto.ExaminationRdto
import ru.ambulance.doctorservice.service.ExaminationService
import java.util.UUID

@Service
class ExaminationServiceImpl(private val examinationRepository: ExaminationRepository,
                             private val doctorMessageService: DoctorMessageServiceImpl) : ExaminationService {

    @Value("\${kafka.topics.examinationRequestTopic}")
    private val examinationRequestTopic: String = "examinationRequestTopic"

    @Transactional
    override fun createNewExamination(examinationRdto: ExaminationRdto): Mono<String> {
        val examination = examinationRdto.toExamination()
        return examinationRepository.save(examination).flatMap {
            val creatingExaminationEvent = CreatingExaminationEvent(
                    examinationId = it.examinationId,
                    hospitalId = examinationRdto.hospitalId,
                    investigationKindIds = examinationRdto.requiredInvestigations,
                    treatmentKindIds = examinationRdto.requiredTreatments,
                    eventId = UUID.randomUUID().toString()
            )
            doctorMessageService.sendMessage(null, examinationRequestTopic, creatingExaminationEvent)
        }.map { examination.examinationId }
    }
}