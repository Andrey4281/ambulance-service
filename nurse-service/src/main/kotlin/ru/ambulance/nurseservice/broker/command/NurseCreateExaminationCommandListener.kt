package ru.ambulance.nurseservice.broker.command

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.examination.CreatingExaminationEvent
import ru.ambulance.nurseservice.broker.AbstractNurseServiceListener
import ru.ambulance.nurseservice.model.entity.InvestigationResult
import ru.ambulance.nurseservice.model.entity.TreatmentResult
import ru.ambulance.nurseservice.service.InvestigationResultService
import ru.ambulance.nurseservice.service.NurseService
import ru.ambulance.nurseservice.service.TreatmentResultService
import java.util.UUID

@Configuration
class NurseCreateExaminationCommandListener(private val nurseService: NurseService,
                                            private val investigationResultService: InvestigationResultService,
                                            private val treatmentResultService: TreatmentResultService): AbstractNurseServiceListener<CreatingExaminationEvent, List<String>>() {

    @Value("\${kafka.topics.examinationRequestTopic}")
    private val examinationRequestTopic: String = "examinationRequestTopic"

    override fun getErrorObject(): List<String> = arrayListOf()

    override fun getTopic(): String = examinationRequestTopic

    @Transactional
    override fun getSuccessHandler(value: CreatingExaminationEvent): Mono<List<String>> {
        var investigationFlux = Flux.empty<String>()
        var treatmentFlux = Flux.empty<String>()

        if (value.investigationKindIds.isNotEmpty()) {
            investigationFlux = Flux.fromIterable(value.investigationKindIds)
                    .flatMap { nurseService.findRequiredNurseWithMinActiveInvestigation(hospitalId = value.hospitalId, investigationKindId = it) }
                    .flatMap { investigationResultService.insert(InvestigationResult(investigationResultId = UUID.randomUUID().toString(),
                            examinationId = value.examinationId, appealId = value.appealId, investigationKindId = it.treatment!!,
                            nurseId = it.id, filePath = null)) }.map { it.investigationResultId }.log()
        }

        if (value.treatmentKindIds.isNotEmpty()){
            treatmentFlux = Flux.fromIterable(value.treatmentKindIds)
                    .flatMap { nurseService.findRequiredNurseWithMinActiveTreatment(hospitalId = value.hospitalId, treatmentKindId = it) }
                    .flatMap { treatmentResultService.insert(TreatmentResult(treatmentResultId = UUID.randomUUID().toString(),
                            examinationId = value.examinationId, appealId = value.appealId, treatmentKindId = it.treatment!!,
                            nurseId = it.id)) }.map { it.treatmentResultId }.log()
        }

        return investigationFlux.mergeWith(treatmentFlux).collectList().log()
    }

    override fun getEventClass(): Class<CreatingExaminationEvent> = CreatingExaminationEvent::class.java

    @Bean("NurseCreateExaminationCommandListener")
    override fun consumer(kafkaProperties: KafkaProperties, objectMapper: ObjectMapper): ApplicationRunner = abstractConsumer(kafkaProperties, objectMapper)
}