package ru.ambulance.nurseservice.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.appeal.UpdateAppealEvent
import ru.ambulance.enums.AppealStatus
import ru.ambulance.nurseservice.broker.outbox.NurseMessageServiceImpl
import ru.ambulance.nurseservice.dao.TreatmentResultRepository
import ru.ambulance.nurseservice.model.dto.TreatmentResultDto
import ru.ambulance.nurseservice.model.entity.TreatmentResult
import ru.ambulance.nurseservice.model.mapper.toDto
import ru.ambulance.nurseservice.model.rdto.TreatmentResultRdto
import ru.ambulance.nurseservice.service.NurseService
import ru.ambulance.nurseservice.service.NurseShiftService
import ru.ambulance.nurseservice.service.TreatmentResultService
import java.util.*

@Service
class TreatmentResultServiceImpl(private val treatmentResultRepository: TreatmentResultRepository,
                                 private val nurseShiftService: NurseShiftService,
                                 private val nurseService: NurseService,
                                 private val nurseMessageService: NurseMessageServiceImpl) : TreatmentResultService {

    @Value("\${kafka.topics.appealCommandTopic}")
    private val appealCommandTopic: String = "appealCommandTopic"

    override fun insert(treatmentResult: TreatmentResult): Mono<TreatmentResult> = treatmentResultRepository.save(treatmentResult)

    @Transactional
    override fun updateTreatmentResult(treatmentResultRdto: TreatmentResultRdto): Mono<String> {
        return treatmentResultRepository.findById(treatmentResultRdto.treatmentResultId).flatMap {
            it.isNewObject = false
            if (treatmentResultRdto.isExecuted != null) {
                it.isExecuted = treatmentResultRdto.isExecuted
            }
            treatmentResultRepository.save(it)
        }.flatMap { treatmentResult ->
            if (treatmentResultRdto.isExecuted != null && treatmentResultRdto.isExecuted) {
                nurseShiftService.findFirstByNurseIdAndIsActiveTrue(treatmentResult.nurseId!!).flatMap {
                    it.activeTreatmentCount = it.activeTreatmentCount - 1
                    nurseShiftService.updateNurseShift(it)
                }.flatMap {
                    nurseService.isExistAvailableTreatmentOrInvestigation(treatmentResult.appealId)
                }.flatMap {
                    if (!it) {
                        nurseMessageService.sendMessage(null, appealCommandTopic, UpdateAppealEvent(
                                appealId = treatmentResult.appealId,
                                appealStatus = AppealStatus.READY_FOR_VERDICT,
                                eventId = UUID.randomUUID().toString()))
                    } else {
                        Mono.just(treatmentResult)
                    }
                }
            } else {
                Mono.just(treatmentResult)
            }
        }.map { treatmentResultRdto.treatmentResultId }
    }

    override fun showTreatmentResultList(appealId: String?, examinationId: String?, nurseId: String?): Flux<TreatmentResultDto> =
            treatmentResultRepository.showProcedureResultList(appealId = appealId, examinationId = examinationId, nurseId = nurseId).map { it.toDto() }
}