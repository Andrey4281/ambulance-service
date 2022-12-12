package ru.ambulance.nurseservice.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.appeal.UpdateAppealEvent
import ru.ambulance.enums.AppealStatus
import ru.ambulance.nurseservice.broker.outbox.NurseMessageServiceImpl
import ru.ambulance.nurseservice.dao.InvestigationResultRepository
import ru.ambulance.nurseservice.model.dto.InvestigationResultDto
import ru.ambulance.nurseservice.model.entity.InvestigationResult
import ru.ambulance.nurseservice.model.mapper.toDto
import ru.ambulance.nurseservice.model.rdto.InvestigationResultRdto
import ru.ambulance.nurseservice.service.InvestigationResultService
import ru.ambulance.nurseservice.service.NurseService
import ru.ambulance.nurseservice.service.NurseShiftService
import java.util.UUID

@Service
class InvestigationResultServiceImpl(private val investigationResultRepository: InvestigationResultRepository,
                                     private val nurseMessageService: NurseMessageServiceImpl,
                                     private val nurseShiftService: NurseShiftService,
                                     private val nurseService: NurseService)
    : InvestigationResultService {

    @Value("\${kafka.topics.appealCommandTopic}")
    private val appealCommandTopic: String = "appealCommandTopic"

    override fun insert(investigationResult: InvestigationResult): Mono<InvestigationResult> = investigationResultRepository.save(investigationResult)

    @Transactional
    override fun updateInvestigationResult(investigationResultRdto: InvestigationResultRdto): Mono<String> {
        return investigationResultRepository.findById(investigationResultRdto.investigationResultId).flatMap {
            it.isNewObject = false
            if (investigationResultRdto.isExecuted != null) {
                it.isExecuted = investigationResultRdto.isExecuted
            }
            if (investigationResultRdto.filePath != null) {
                it.filePath = investigationResultRdto.filePath
            }
            investigationResultRepository.save(it)
        }.flatMap { investigationResult ->
            if (investigationResultRdto.isExecuted != null && investigationResultRdto.isExecuted) {
                nurseShiftService.findFirstByNurseIdAndIsActiveTrue(investigationResult.nurseId!!).flatMap {
                    it.activeInvestigationCount = it.activeInvestigationCount - 1
                    nurseShiftService.updateNurseShift(it)
                }.flatMap {
                    nurseService.isExistAvailableTreatmentOrInvestigation(investigationResult.appealId)
                }.flatMap {
                    if (!it) {
                        nurseMessageService.sendMessage(null, appealCommandTopic, UpdateAppealEvent(
                                appealId = investigationResult.appealId,
                                appealStatus = AppealStatus.READY_FOR_VERDICT,
                                eventId = UUID.randomUUID().toString()))
                    } else {
                        Mono.just(investigationResult)
                    }
                }
            } else {
                Mono.just(investigationResult)
            }
        }.map { investigationResultRdto.investigationResultId }
    }

    override fun showInvestigationResultList(appealId: String?, examinationId: String?, nurseId: String?): Flux<InvestigationResultDto> =
            investigationResultRepository.showProcedureResultList(appealId = appealId, examinationId = examinationId, nurseId = nurseId).map { it.toDto() }
}