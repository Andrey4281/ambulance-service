package ru.ambulance.nurseservice.broker.command

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Persistable
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.appeal.UpdateAppealEvent
import ru.ambulance.broker.events.examination.CreatingExaminationEvent
import ru.ambulance.broker.events.outbox.OutboxEvent
import ru.ambulance.enums.AppealStatus
import ru.ambulance.nurseservice.broker.AbstractNurseServiceListener
import ru.ambulance.nurseservice.broker.outbox.NurseMessageServiceImpl
import ru.ambulance.nurseservice.model.entity.InvestigationResult
import ru.ambulance.nurseservice.model.entity.TreatmentResult
import ru.ambulance.nurseservice.service.InvestigationResultService
import ru.ambulance.nurseservice.service.NurseService
import ru.ambulance.nurseservice.service.NurseShiftService
import ru.ambulance.nurseservice.service.TreatmentResultService
import java.util.*

@Configuration
class NurseCreateExaminationCommandListener(private val nurseService: NurseService, private val investigationResultService: InvestigationResultService, private val treatmentResultService: TreatmentResultService, private val nurseMessageService: NurseMessageServiceImpl, private val nurseShiftService: NurseShiftService) : AbstractNurseServiceListener<CreatingExaminationEvent, OutboxEvent>() {

    @Value("\${kafka.topics.examinationRequestTopic}")
    private val examinationRequestTopic: String = "examinationRequestTopic"

    @Value("\${kafka.topics.appealCommandTopic}")
    private val appealCommandTopic: String = "appealCommandTopic"

    @Autowired
    private lateinit var transactionalOperator: TransactionalOperator

    override fun getErrorObject(): OutboxEvent = OutboxEvent(eventId = "", messageKey = "", eventBodyJson = "", sendToTopic = "")

    override fun getTopic(): String = examinationRequestTopic

    override fun getSuccessHandler(value: CreatingExaminationEvent): Mono<OutboxEvent> {
        var investigationFlux = Flux.empty<Persistable<String>>()
        var treatmentFlux = Flux.empty<Persistable<String>>()

        if (value.investigationKindIds.isNotEmpty()) {
            investigationFlux = Flux.fromIterable(value.investigationKindIds).flatMap {
                nurseService.findRequiredNurseWithMinActiveInvestigation(hospitalId = value.hospitalId, investigationKindId = it)
            }.flatMap {
                investigationResultService.insert(InvestigationResult(investigationResultId = UUID.randomUUID().toString(),
                        examinationId = value.examinationId, appealId = value.appealId,
                        investigationKindId = it.treatment!!, nurseId = it.id, filePath = null))
            }.flatMap {
                if (it.nurseId != null) {
                    nurseShiftService.findFirstByNurseIdAndIsActiveTrue(it.nurseId).flatMap {
                        it.activeInvestigationCount = it.activeInvestigationCount + 1
                        nurseShiftService.updateNurseShift(it)
                    }
                } else {
                    Mono.just(it)
                }
            }
        }

        if (value.treatmentKindIds.isNotEmpty()) {
            treatmentFlux = Flux.fromIterable(value.treatmentKindIds).flatMap {
                nurseService.findRequiredNurseWithMinActiveTreatment(hospitalId = value.hospitalId, treatmentKindId = it)
            }.flatMap {
                treatmentResultService.insert(TreatmentResult(treatmentResultId = UUID.randomUUID().toString(),
                        examinationId = value.examinationId, appealId = value.appealId,
                        treatmentKindId = it.treatment!!, nurseId = it.id))
            }.flatMap {
                if (it.nurseId != null) {
                    nurseShiftService.findFirstByNurseIdAndIsActiveTrue(it.nurseId).flatMap {
                        it.activeTreatmentCount = it.activeTreatmentCount + 1
                        nurseShiftService.updateNurseShift(it)
                    }
                } else {
                    Mono.just(it)
                }
            }
        }

        return investigationFlux.mergeWith(treatmentFlux).collectList().flatMap {
            nurseMessageService.sendMessage(null, appealCommandTopic, UpdateAppealEvent(appealId = value.appealId, appealStatus = AppealStatus.INVESTIGATION_TREATMENT, eventId = UUID.randomUUID().toString())).`as` { transactionalOperator.transactional(it) }
        }
    }

    override fun getEventClass(): Class<CreatingExaminationEvent> = CreatingExaminationEvent::class.java

    @Bean("NurseCreateExaminationCommandListener")
    override fun consumer(kafkaProperties: KafkaProperties, objectMapper: ObjectMapper): ApplicationRunner = abstractConsumer(kafkaProperties, objectMapper)
}