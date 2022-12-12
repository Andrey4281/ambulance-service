package ru.ambulance.appeal.broker.saga

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono
import ru.ambulance.appeal.broker.AbstractAppealServiceListener
import ru.ambulance.appeal.model.entity.Appeal
import ru.ambulance.appeal.service.AppealService
import ru.ambulance.broker.events.appeal.DoctorResponseOnCreatingAppealEvent
import ru.ambulance.enums.AppealStatus
import javax.annotation.PostConstruct

@Configuration
class CreatingAppealSagaDoctorResponseListener : AbstractAppealServiceListener<DoctorResponseOnCreatingAppealEvent, Appeal>() {
    @PostConstruct
    fun init() {
        println("CreatingAppealSagaDoctorResponseListener INITED")
    }

    @Value("\${kafka.topics.appealResponseTopic}")
    private val appealResponseTopic: String = "appealResponseTopic"

    @Autowired
    private lateinit var appealService: AppealService;

    @Autowired
    private lateinit var  transactionalOperator: TransactionalOperator

    override fun getTopic(): String = appealResponseTopic

    override fun getSuccessHandler(value: DoctorResponseOnCreatingAppealEvent): Mono<Appeal> {
        return value.appealId.let { appealService.findById(it) }.flatMap {
            it.isNewObject = false
            if (value.isSuccess) {
                it.appealStatus = AppealStatus.ASSIGNED.name
                it.currentDoctorId = value.doctorId
            } else {
                it.appealStatus = value.appealStatus.name
            }
            appealService.save(it).`as` { transactionalOperator.transactional(it) }
        }
    }

    override fun getEventClass(): Class<DoctorResponseOnCreatingAppealEvent> = DoctorResponseOnCreatingAppealEvent::class.java

    override fun getErrorObject(): Appeal = Appeal(appealId = "", authorId = "",
            description = "", primaryPatientStatus = "",
            patientId = "", primaryRequiredDoctor = "",
            hospitalId = "")

    @Bean("CreatingAppealSagaDoctorResponseListener")
    override fun consumer(kafkaProperties: KafkaProperties, objectMapper: ObjectMapper): ApplicationRunner = abstractConsumer(kafkaProperties, objectMapper)
}